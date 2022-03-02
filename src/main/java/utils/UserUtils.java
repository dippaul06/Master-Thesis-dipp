package utils;

import tech.tablesaw.api.*;
import tech.tablesaw.io.csv.CsvWriteOptions;
import tech.tablesaw.io.csv.CsvWriter;
import model_Final.Model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static system.Contracts.checkArgument;
import static model_Final.Model.UserState.NOT_AVAILABLE;
import static utils.TimeUtils.dateToLocalDateTime;

// ----------------------------------------------
//  USER STATS.
// ----------------------------------------------
public class UserUtils {

    private final static int DEFAULT = -99;

    public enum _5G {
        IS_5G,
        IS_NOT_5G,
        IS_BOTH,
        NOT_KNOWN
    }

    private final LongColumn uidCol = LongColumn.create("id");
    private final IntColumn indexCol = IntColumn.create("index");
    private final DateTimeColumn createdAt = DateTimeColumn.create("date");
    private final BooleanColumn verifiedCol = BooleanColumn.create("verified");
    private final BooleanColumn protectedCol = BooleanColumn.create("protected");
    private final BooleanColumn deletedCol = BooleanColumn.create("deleted");
    private final IntColumn friendsCountCol = IntColumn.create("friends_count");
    private final IntColumn followersCountCol = IntColumn.create("followers_count");
    private final IntColumn statusesCountCol = IntColumn.create("statuses_count");
    private final IntColumn favouritesCountCol = IntColumn.create("favourites_count");
    private final StringColumn locationCol = StringColumn.create("location");
    private final StringColumn screenNameCol = StringColumn.create("screen_name");

    private final IntColumn yearCol = IntColumn.create("year");
    private final StringColumn descriptionCol = StringColumn.create("description");

    private final StringColumn is5gCol = StringColumn.create("5g");

    public void addUser(User user, int index) {
        if (user.state == NOT_AVAILABLE)
            return;
        var date = dateToLocalDateTime(user.createdAt());
        indexCol.append(index);
        uidCol.append(user.id());
        createdAt.append(date);
        verifiedCol.append(user.isVerified());
        protectedCol.append(user.isProtected());
        //Commenting - Dip
//        deletedCol.append(user.isDeleted());
        friendsCountCol.append(user.friendsCount());
        followersCountCol.append(user.followerCount());
        statusesCountCol.append(user.statusesCount());
        favouritesCountCol.append(user.favouritesCount());
        locationCol.append(user.location());
        screenNameCol.append(user.screenName());
        yearCol.append(date.getYear());
        is5gCol.append(_5G.NOT_KNOWN.name());
        descriptionCol.append("'" + user.description().replace('\n', ' ') + "'");
    }

    public void addUser(User user) {
        if (user.state == NOT_AVAILABLE)
            return;
        var date = dateToLocalDateTime(user.createdAt());
        indexCol.append(DEFAULT);
        uidCol.append(user.id());
        createdAt.append(date);
        verifiedCol.append(user.isVerified());
        protectedCol.append(user.isProtected());
        //Commenting - Dip
//        deletedCol.append(user.isDeleted());
        friendsCountCol.append(user.friendsCount());
        followersCountCol.append(user.followerCount());
        statusesCountCol.append(user.statusesCount());
        favouritesCountCol.append(user.favouritesCount());
        locationCol.append(user.location());
        screenNameCol.append(user.screenName());
        yearCol.append(date.getYear());
        is5gCol.append(_5G.NOT_KNOWN.name());
        descriptionCol.append("'" + user.description().replace('\n', ' ') + "'");
    }

    public void addUser(User user, _5G _5g) {
        if (user.state == NOT_AVAILABLE)
            return;
        var date = dateToLocalDateTime(user.createdAt());
        indexCol.append(DEFAULT);
        uidCol.append(user.id());
        createdAt.append(date);
        verifiedCol.append(user.isVerified());
        protectedCol.append(user.isProtected());
        //Commenting - Dip
//        deletedCol.append(user.isDeleted());
        friendsCountCol.append(user.friendsCount());
        followersCountCol.append(user.followerCount());
        statusesCountCol.append(user.statusesCount());
        favouritesCountCol.append(user.favouritesCount());
        locationCol.append(user.location());
        screenNameCol.append(user.screenName());
        yearCol.append(date.getYear());
        is5gCol.append(_5g.name());
        descriptionCol.append("'" + user.description().replace('\n', ' ') + "'");
    }

    Table toTable() {
        return Table.create(
//                    indexCol,
                uidCol,
                createdAt,
                verifiedCol,
                protectedCol,
                deletedCol,
                friendsCountCol,
                followersCountCol,
                statusesCountCol,
                favouritesCountCol,
                locationCol,
//                    screenNameCol,
                yearCol,
                is5gCol
//                    descriptionCol
        );
    }

    public UserUtils write(Path dir, String name) throws IOException {
        checkArgument(Files.isDirectory(dir));
        checkArgument(Files.notExists(dir.resolve(name)));
        var file = Files.createFile(dir.resolve(name));
        CsvWriteOptions options =
                CsvWriteOptions.builder(file.toFile())
                        .header(true)
                        .separator(',')
                        .build();
        new CsvWriter().write(toTable(), options);
        return this;
    }
}
