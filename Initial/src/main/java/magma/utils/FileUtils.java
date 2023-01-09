package magma.utils;

import com.google.common.collect.Sets;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.io.MoreFiles.getFileExtension;
import static com.google.common.io.MoreFiles.getNameWithoutExtension;
import static java.lang.Math.*;
import static java.lang.String.format;
import static java.nio.file.Files.*;
import static java.nio.file.StandardOpenOption.*;
import static java.nio.file.attribute.PosixFilePermission.*;
import static java.util.regex.Pattern.MULTILINE;
import static org.apache.commons.io.FileUtils.listFiles;
import static magma.utils.MainUtils.MAX_ARRAY_LENGTH;
import static magma.utils.MathUtils.MAX_INT_POW_2;
import static magma.utils.MathUtils.fitsLongInInt;
import static magma.utils.MemoryUtils.MEM_PAGE_SIZE;

// ------------------------------------------------------------
//                       FILE UTILS
// ------------------------------------------------------------
//
public enum FileUtils {
    ;

    static final int NEW_LINE = 10;

    static final Charset UTF8 = Charset.forName("UTF-8");

    public static boolean fileEndingEquals(Path pth, String ending) {
        return pth.getFileName().toString().endsWith(ending);
    }

    public static Set<Path> recursiveFiles(Path folder, String ending) {
        var col = listFiles(
                folder.toFile(),
                new RegexFileFilter("^(.*" + ending + ")"),
                DirectoryFileFilter.DIRECTORY);
        var paths = new HashSet<Path>();
        col.stream()
                .map(File::toPath)
                .forEach(paths::add);
        return paths;
    }

    public static void copyFileFromJarTo(Class<?> clazz, String source, Path dest) {
        try { org.apache.commons.io.FileUtils.copyInputStreamToFile(clazz.getResourceAsStream(source), dest.toFile()); }
        catch (IOException e) { e.printStackTrace(); }
    }

    // https://goo.gl/JVF5oP
    public static long countLines(final Path pth) {
        checkState(isRegularFile(pth), "must be a reg file");
        var res = 0L;
        try (var inp = new BufferedInputStream(new FileInputStream(pth.toFile()))) {
            var buf = new byte[1024];
            var dne = inp.read(buf);
            if (dne == -1) return 0;
            while (dne == buf.length) {
                for (int i = 0; i < buf.length; ) {
                    if (buf[i++] == '\n') {
                        ++res;
                    }
                }
                dne = inp.read(buf);
            }
            while (dne != -1) {
                for (int i = 0; i < dne; ++i) {
                    if (buf[i] == '\n') {
                        ++res;
                    }
                }
                dne = inp.read(buf);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return res == 0 ? 1 : res;
    }

    public static boolean contentEquals(Path srcPth, Path tgtPth) {
        try {
            final boolean srcExist = Files.exists(srcPth);
            if (srcExist != Files.exists(tgtPth)) return false;
            if (!srcExist) return true;
            if (isDirectory(srcPth) || isDirectory(tgtPth)) return false;
            if (srcPth.equals(tgtPth)) return true;
            if (Files.size(srcPth) != Files.size(tgtPth)) return false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        final int optSize = optBufferLen(srcPth);
        ByteBuffer srcBuf = ByteBuffer.allocateDirect(optSize);
        ByteBuffer tgtBuf = ByteBuffer.allocateDirect(optSize);
        try(var srcCha = FileChannel.open(srcPth, READ)) {
            try(var tgtCha = FileChannel.open(tgtPth, READ)) {
                while (srcCha.position() < srcCha.size()) {

                    srcCha.read(srcBuf);
                    tgtCha.read(tgtBuf);
                    srcBuf.flip();
                    tgtBuf.flip();
                    while (srcBuf.hasRemaining()) {
                        if (srcBuf.get() != tgtBuf.get()) {
                            return false;
                        }
                    }
                    srcBuf.clear();
                    tgtBuf.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void printDirContent(Path pth) {
        checkState(isDirectory(pth), "NOT A DIR");
        try {
            Files.walk(pth)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Path merge(Path outPth, Path... inPth) {
        try(var outCha = FileChannel.open(outPth, CREATE, WRITE)) {
            for(int i = 0; i < inPth.length; i++) {
                try(var inCha = FileChannel.open(inPth[i], READ)) {
                    for(long p = 0, l = inCha.size(); p < l; ) {
                        p += inCha.transferTo(p, l - p, outCha);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return outPth;
    }


    public static void clearDir(Path pth) {
        checkState(isDirectory(pth), "NOT A DIR");
        try {
            Files.walk(pth)
                    .filter(p -> !p.equals(pth))
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirWithContent(Path pth) {
        checkState(isDirectory(pth), "NOT A DIR");
        try {
            Files.walk(pth)
                    .filter(p -> !p.equals(pth))
                    .map(Path::toFile)
                    .forEach(File::delete);
            Files.delete(pth);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stream<Path> fileStreamOf(Path pth) {
        checkArgument(isDirectory(pth), pth + "is not a directory");
        try { return Files.walk(pth).filter(f -> Files.isRegularFile(f)); }
        catch (IOException e) { e.printStackTrace(); }
        return Stream.empty();
    }

    public static void forEachFileIn(Path pth, Consumer<? super Path> con) {
        checkArgument(isDirectory(pth), pth + "is not a directory");
        try { Files.walk(pth).filter(f -> Files.isRegularFile(f)).forEach(con); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static long findFirstIndexOf(final ByteBuffer buf, final FileChannel cha, final char c) throws IOException {
        long cnt = 0L;
        int red = cha.read(buf.clear());
        while (red != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                if (buf.get() == c)
                    return cnt;
                ++cnt;
            }
            red = cha.read(buf.clear());
        }
        return -1;
    }


    public static long findLastIndexOf(final ByteBuffer buf, final FileChannel cha, final char c) throws IOException {
        long index = cha.size();
        long start = max(cha.size() - buf.capacity(), 0);
        for (;start >= 0 && index > 0; start = max(start - buf.capacity(), 0)) {
            cha.read(buf.clear().limit((int) min(index, buf.capacity())), start);
            while (buf.position() > 0) {
                --index;
                if (buf.get(buf.position() - 1) == c) {
                    return index;
                }
                buf.position(buf.position() - 1);
            }

        }
        return -1;
    }


    public static boolean endsWith(final ByteBuffer buf, final FileChannel cha, final char c) throws IOException {
        checkArgument(cha.read(buf.clear().limit(1), cha.size() - 1) == 1);
        return buf.get(0) == c;
    }

    public static CompletableFuture<List<Path>> splitBySizeAndNewLine(Path srcPth, Path tgtDir, double chunkSizeMb) throws IOException {
        final long cnkLen = MemoryUtils.MBToBytes(chunkSizeMb);
        final long srcLen = Files.size(srcPth);
        final int tgtNum = toIntExact((srcLen/cnkLen));
        var lst = new ArrayList<Long[]>(tgtNum + 1);
        var buf = ByteBuffer.allocateDirect(optBufferLen(srcPth));
        long bytesRead = 0L;
        long currentChunk = 0L;
        long startPos = 0L;
        long chunkCount = 0L;
        try(var fileChannel = FileChannel.open(srcPth, READ)) {
            while (fileChannel.position() < fileChannel.size()) {
                fileChannel.read(buf);
                buf.flip();
                while (buf.hasRemaining()) {
                    ++bytesRead;
                    ++currentChunk;
                    if (buf.get() == '\n') {
                        if (currentChunk >= cnkLen) {
                            currentChunk = 0L;
                            lst.add(new Long[] { ++chunkCount, startPos, bytesRead });
                            startPos = bytesRead;
                        }
                    }
                }
                buf.clear();
            }
        }
        if (currentChunk > 0) {
            lst.add(new Long[] { ++chunkCount, startPos, bytesRead });
        }
        var tmp = lst.stream()
                .map(a ->
                        CompletableFuture.supplyAsync(() -> {
                            var pth = newFileName(tgtDir, srcPth, toIntExact(a[0]));
                            try(var srcCha = FileChannel.open(srcPth, READ)) {
                                try(var tgtCha = FileChannel.open(pth, CREATE, WRITE)) {
                                    srcCha.transferTo(a[1], a[2] - a[1], tgtCha);
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            return pth;
                        })
                ).collect(Collectors.toList());
        return FutureUtils.allAsList(tmp);
    }


    public static CompletableFuture<List<Path>> splitBySize(Path srcPth, Path tgtPth, double chunkSizeMb) throws IOException {
        final var cnkLen = MemoryUtils.MBToBytes(chunkSizeMb);
        final var srcLen = Files.size(srcPth);
        final var remain = srcLen % cnkLen;
        final var tgtNum = toIntExact((srcLen/cnkLen));

        long pos = 0L, cnt = 0L;
        var lst = new ArrayList<Long[]>(tgtNum + 1);
        for (; cnt < tgtNum; pos += cnkLen) lst.add(new Long[]{ ++cnt, pos, cnkLen, pos + cnkLen, 0L });
        if (remain > 0) lst.add(new Long[]{ ++cnt, pos, remain, pos + remain, 0L });

        var tmp = lst.stream()
                .map(a ->
                        CompletableFuture.supplyAsync(() -> {
                            var pth = newFileName(tgtPth, srcPth, toIntExact(a[0]));
                            try(var srcCha = FileChannel.open(srcPth, READ)) {
                                try(var tgtCha = FileChannel.open(pth, CREATE, WRITE)) {
                                    srcCha.transferTo(a[1], a[2], tgtCha);
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            return pth;
                        })
                ).collect(Collectors.toList());
        return FutureUtils.allAsList(tmp);
    }


    private static Path newFileName(final Path dir, final Path src, int cnt) {
        return dir.resolve(format("%1$s%2$010d.%3$s", getNameWithoutExtension(src), cnt, getFileExtension(src)));
    }


    public static Path[] splitByLine(final Path src, final Path tgt, final double chunkSizeMb) throws IOException {
        checkState(isRegularFile(src), "must be a reg file");
        checkState(isDirectory(tgt), "must be a dir");
        checkState(chunkSizeMb > 0, "wrong Max Mb");
        final var tgtFileSize = MemoryUtils.MBToBytes(chunkSizeMb);
        final var srcFileSize = Files.size(src);
        final var tgtFileNum = toIntExact((srcFileSize / tgtFileSize) + 1L);
        final var tgtFiles = new ArrayList<Path>(tgtFileNum);
        var srcBuf = ByteBuffer.allocateDirect(optBufferLen(src) * 1024);
        long YYY = optBufferLen(tgt) * 1024;
        var tgtBuf = UTF8.decode(ByteBuffer.allocateDirect(optBufferLen(tgt) * 1024));
        int tgtFileCnt = 0;
        var srcChannel = FileChannel.open(src);
        var srcBytesRead = srcChannel.read(srcBuf);
        var tgtFileName = newFileName(tgt, src, tgtFileCnt);
        var tgtChannel = FileChannel.open(tgtFileName, CREATE, WRITE);
        tgtFiles.add(tgtFileName);
        long tgtFileCurSize = 0;
        while (srcBytesRead != -1) {
            srcBuf.flip();
            while (srcBuf.hasRemaining()) {
                byte byt = srcBuf.get();
                switch (byt) {
                    case NEW_LINE:
                        if (tgtBuf.length() == YYY) System.out.println("FULL" + YYY);
                        tgtBuf.put((char) byt);
                        tgtChannel.write(UTF8.encode(tgtBuf.flip()));
                        tgtBuf.clear();

                        if(++tgtFileCurSize >= tgtFileSize) {
                            tgtFileCurSize = 0;
                            tgtFileName = newFileName(tgt, src, ++tgtFileCnt);
                            tgtFiles.add(tgtFileName);
                            tgtChannel.close();
                            tgtChannel = FileChannel.open(tgtFileName, CREATE, WRITE);
                        }
                        break;
                    default:
                        if (tgtBuf.length() == YYY) {

                            System.out.println("FULL" + YYY);
                        }
                        tgtFileCurSize++;
                        tgtBuf.put((char) byt);
                        break;
                }
            }
            srcBuf.clear();
            srcBuf.put(UTF8.encode(tgtBuf.flip()));
            tgtBuf.clear();
            srcBytesRead = srcChannel.read(srcBuf);
        }
        srcChannel.close();
        return tgtFiles.toArray(new Path[0]);
    }


    public static int optBufferLen(final Path pth) {
        if (OPT_BUF_LEN > 0) return OPT_BUF_LEN;
        long res = MEM_PAGE_SIZE;
        try { res = getFileStore(pth).getBlockSize(); }
        catch (IOException e) { e.printStackTrace(); }
        return fitsLongInInt(res)? (int) res: MAX_INT_POW_2;
    }


    private static int optBufferLen() {
        var tmp = optDiskBlockSize();
        if (fitsLongInInt(tmp)) return (int) tmp;
        else if (tmp < 1) return -1;
        return MAX_INT_POW_2;
    }


    private static long optDiskBlockSize() {
        var max = Long.MIN_VALUE; var min = Long.MAX_VALUE;
        for (FileStore fs: FileSystems.getDefault().getFileStores()) {
            try {
                var tmp = fs.getBlockSize();
                if(tmp > max) max = tmp;
                if(tmp < min) min = tmp;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return max == min? min: -1;
    }

    public static boolean verifyFollowerSplit(List<Path> pths) {
        final var regex = "\\d{1,19}";
        final var pattern = Pattern.compile(regex, MULTILINE);
        final ConcurrentHashMap<Path, Long[]> errors = new ConcurrentHashMap<>();
        var futures = pths.stream().map(pth ->
                CompletableFuture.supplyAsync(() -> {
                    final Matcher matcher = pattern.matcher("");
                    long lineCnt = 0L;
                    try (Scanner scanner = new Scanner(pth, StandardCharsets.UTF_8)) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            if (line.length() >= MAX_ARRAY_LENGTH) {
                                errors.put(pth, new Long[] { lineCnt, -1L });
                                return false;
                            }
                            String[] arr = line.split(",");
                            lineCnt++;
                            for (int i = 0; i < arr.length; i++) {
                                if (!matcher.reset(arr[i]).matches()) {
                                    errors.put(pth, new Long[] { lineCnt, (long) i });
                                    return false;
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(pth + " OK");
                    return true;
                })).collect(Collectors.toList());

        var result = FutureUtils.allAsList(futures)
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                })
                .join().stream()
                .reduce((a, b) -> a && b)
                .orElse(false);
        errors.forEach((pth, err) -> System.out.println(pth + ": " + Arrays.toString(err)));
        return result;
    }

    public static boolean verifyTwitterUserSplit(List<Path> pths) {
        final var regex = "\\d{1,19}[\\t]\\d{1,19}";
        final var pattern = Pattern.compile(regex, MULTILINE);

        var futures = pths.stream().map(pth ->
                CompletableFuture.supplyAsync(() -> {
                    final Matcher ma = pattern.matcher("");
                    boolean result = true;
                    try (Scanner scanner = new Scanner(pth, StandardCharsets.UTF_8)) {
                        while (scanner.hasNextLine()) {
                            ma.reset(scanner.nextLine());
                            if (!ma.matches()) {
                                result = false;
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                })).collect(Collectors.toList());

        return FutureUtils.allAsList(futures).join().stream().reduce((a, b) -> a && b).orElse(false);
    }

    public static BufferedReader newBufferedReader(Path path) {
        checkArgument(Files.isRegularFile(path));
        BufferedReader bReader = null;
        try {
            var fReader = new FileReader(path.toFile());
            bReader = new BufferedReader(fReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bReader;
    }

    public static List<String> readLinesWithoutHeader(Path pth) {
        var reader = FileUtils.newBufferedReader(pth);
        var lines = reader.lines().collect(Collectors.toList());
        lines.remove(0);
        try { reader.close(); }
            catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    public static BufferedWriter newBufferedWriter(Path path, boolean append) {
        checkArgument(Files.isRegularFile(path));
        BufferedWriter bWriter = null;
        try {
            var fWriter = new FileWriter(path.toFile(), append);
            bWriter = new BufferedWriter(fWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bWriter;
    }

    public static BufferedWriter newBufferedWriterAppend(Path path) {
        return newBufferedWriter(path, true);
    }

    public static FileOutputStream newFileOutputStream(Path path) {
        FileOutputStream res = null;
        try { res =  new FileOutputStream(path.toFile()); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        return res;
    }

    public static BufferedOutputStream newBufferedOutStream(Path path) {
        return new BufferedOutputStream(newFileOutputStream(path));
    }

    public static GZIPOutputStream newGzipOutStream(Path path) {
        GZIPOutputStream res = null;
        try { res = new GZIPOutputStream(newFileOutputStream(path)); }
        catch (IOException e) { e.printStackTrace(); }
        return res;
    }

    public static boolean isDirEmpty(Path dir) {
        checkArgument(isDirectory(dir));
        return fileStreamOf(dir).count() == 0;
    }

    // ----------------------------------------------
    //  NEW FILE.
    // ----------------------------------------------
    public static Path newFile(Path dir, String name) {
        checkArgument(Files.isDirectory(dir));
        var filePth = dir.resolve(name);
        return newFile(filePth);
    }

    public static Path newFile(Path filePth) {
        checkArgument(Files.notExists(filePth));
        try { Files.createFile(filePth); }
        catch (IOException e) { e.printStackTrace(); }
        return filePth;
    }

    // ----------------------------------------------
    //  RANDOM FILE PATH.
    // ----------------------------------------------
    public static Path newRandomPath(Path dir) {
        checkArgument(isDirectory(dir));
        return dir.resolve(String.valueOf(UUID.randomUUID()));
    }


    // ----------------------------------------------
    //  CONSTANTS.
    // ----------------------------------------------
    //
    private static final int OPT_BUF_LEN = optBufferLen();

    public static final Set<PosixFilePermission> DEFAULT_PERMISSIONS =

            Sets.immutableEnumSet(OWNER_READ, OWNER_WRITE, GROUP_READ, GROUP_WRITE, OTHERS_READ, OTHERS_WRITE);
}


