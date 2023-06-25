
static long encode(short[] array) {
    long encodedValue = 0;
    for (int i = 0; i < array.length; i++) {
        encodedValue |= (long) array[i] << (i * 2);
        }
    return encodedValue;
}


static short[] decode(long encodedValue) {
    short[] decodedArray = new short[9];
    for (int i = 0; i < 9; i++) {
        decodedArray[i] = (short) ((encodedValue >> (i * 2)) & 3);
        }
    return decodedArray;
}



public static void main(String[] args) {
    short[] array1 = {1, 2, 3, 2, 1, 3, 1, 1, 2};
    short[] array2 = {2, 2, 3, 2, 1, 3, 1, 1, 2};
    short[] array3 = {1, 1, 1, 1, 1, 1, 1, 1, 0};
    var arr = array3;
    System.out.println("Array 1: " + Arrays.toString(arr));
    long encodedValue = encode(arr);
    System.out.println("Encoded value: " + encodedValue);
    short[] decodedArray = decode(encodedValue);
    System.out.print("Decoded array: " + Arrays.toString(decodedArray));
}