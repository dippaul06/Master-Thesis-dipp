package graph;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import utils.BsonUtils;
//import model.Model;
//import model.Model.*;
//import model.Store;
import model_Final.Model;
import model_Final.Model.*;
import model_Final.Store;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static system.Contracts.*;
import static utils.Utils.extractCreatedAtLclDateTime;

// ----------------------------------------------
//  CONTACTS.
// ----------------------------------------------
// TODO -1 bei retweets: die Eltern der Retweets sind alle
// Dummies obwohl die Tweets existieren
public enum Contacts {
    get;

    static int CNT = 0;

    // ----------------------------------------------
    //  CONTACT.
    // ----------------------------------------------
    public static abstract class Contact<C extends Model.Status<C>> {
        public final long parentUserId;
        public final long childUserId;
        final User parentUser, childUser;
        final ThreadSource<?> parent;
        final LocalDateTime moment;
        final C child;

        protected Contact(C _child, ThreadSource<?> _parent) {
            moment = extractCreatedAtLclDateTime(_child.oldBson());
            checkArgument(!_child.isDummy());
            parentUserId = _parent.userId();
            childUserId = _child.userId();
            if (isNull(Store.get.users.get(parentUserId))) {
                Store.get.users.put(parentUserId, User.newUserFromId(parentUserId));
                System.out.println("ADDED : " + ++CNT + " / " + Store.get.users.size() + " ID: " + parentUserId + " PARENT ");
                System.out.println(this.getClass().getName());
                System.out.println(BsonUtils.beautiful(_parent.oldBson()));
            }
            if (isNull(Store.get.users.get(childUserId))) {
                Store.get.users.put(childUserId, User.newUserFromId(childUserId));
                System.out.println("ADDED : " + ++CNT + " / " + Store.get.users.size() + " ID: " + childUserId + " CHILD ");
                System.out.println(BsonUtils.beautiful(_child.oldBson()));
            }
            parentUser = checkNotNull(Store.get.users.get(parentUserId), "PARENT ID: " + parentUserId);
            childUser = checkNotNull(Store.get.users.get(childUserId), "CHILD ID: " + childUserId);
            parent = _parent;
            child = _child;
        }
        public String toString() { return this.getClass().getName() + " " + moment; }
        public LocalDateTime moment() { return moment; }
        public User childUser() { return childUser; }
        public User parentUser() { return parentUser; }
        public long childUserId() { return childUserId; }
        public long parentUserId() { return parentUserId; }
    }

    // ----------------------------------------------
    //  RETWEETED-CONTACT.
    // ----------------------------------------------
    public static class RetweetContact extends Contact<Retweet> {
        protected RetweetContact(Retweet _child, ThreadSource<?> _parent) {
            super(_child, _parent);
        }
    }

    // ----------------------------------------------
    //  QUOTED-CONTACT.
    // ----------------------------------------------
    public static class QuoteContact extends Contact<Quote> {
        protected QuoteContact(Quote _child, ThreadSource<?> _parent) {
            super(_child, _parent);
        }
    }

    // ----------------------------------------------
    //  COMMENTED-CONTACT.
    // ----------------------------------------------
    public static class CommentContact extends Contact<Reply> {
        int distance = -1;
        protected CommentContact(Reply _child, ThreadSource<?> _parent) {
            super(_child, _parent);
        }
    }

    public Set<RetweetContact> retweetContacts = new HashSet<>();
    public Set<CommentContact> commentContacts = new HashSet<>();
    public Set<QuoteContact> quotedContacts = new HashSet<>();

    static Long2LongMap COMMON_COMMENTS = new Long2LongOpenHashMap();
    static boolean checkIfCommonCommentExists(Reply r1, Reply r2) {
        return (COMMON_COMMENTS.containsKey(r1.id())
                && COMMON_COMMENTS.get(r1.id()) == r2.id())
                    || (COMMON_COMMENTS.containsKey(r2.id())
                        && COMMON_COMMENTS.get(r2.id()) == r1.id());
    }

    public int errorCnt = 0;
    public int repairedCnt = 0;

    // TODO sanity checks
    void addContacts(Status<?> status) {

        if (status instanceof Retweet) {
            var retweet = (Retweet) status;
            checkState(!retweet.isDummy());
            var parent = checkNotNull(retweet.parent());
            checkState(parent.retweets().get(retweet.id()).equals(retweet));
            if (parent.userId() == -1L) {
                errorCnt++;
                if (Store.get.tweets.containsKey(retweet.parent().id())) {
                    repairedCnt++;
                    parent = Store.get.tweets.get(retweet.parent().id());
                }
                else {
                    return; // VORSICHT!!!!!!
                }
            }
            retweetContacts.add(new RetweetContact(retweet, parent));
        }

        else if (status instanceof Quote) {
            var quote = (Quote) status;
            checkState(!quote.isDummy());
            var parent = checkNotNull(quote.parent());
            checkState(parent.quotes().get(quote.id()).equals(quote));
            if (parent.userId() == -1L) return;
            quotedContacts.add(new QuoteContact(quote, parent));
        }

        else if (status instanceof Reply) {
            var reply = (Reply) status;
            checkState(!reply.isDummy());
            var parent = checkNotNull(reply.parent());
            if (parent.userId() == -1L) return;
            commentContacts.add(new CommentContact(reply, parent));
            // comment to comment contacts
            for (var r2 : parent.replies().values()) {
                if (!checkIfCommonCommentExists(reply, r2)
                        && !(reply.id() == r2.id())) {
                    commentContacts.add(new CommentContact(reply, r2));
                    COMMON_COMMENTS.put(reply.id(), r2.id());
                }
            }
        }

        // TODO
        // QUOTED-REPLY & QUOTED-RETWEET
        // I think the best way to do this is
        // to add one Quote and one Reply for
        // each quoted reply. We really want
        //
    }

    public void loadContacts(Store store) {
        store.stati().forEachRemaining(this::addContacts);
        System.out.println("RETWEET CONTACTS:   " + retweetContacts.size());
        System.out.println("COMMENT CONTACTS:   " + commentContacts.size());
        System.out.println("QUOTE CONTACTS:     " + quotedContacts.size());
    }

    public Iterator<Contact<? extends Status<?>>> contacts() {
        return Iterables.concat(
                retweetContacts,
                commentContacts,
                quotedContacts
        ).iterator();
    }

    @SuppressWarnings("unchecked")
    public Stream<Contact<?>> contactsByTime() {
        return (Stream<Contact<?>>) Stream.of(retweetContacts, commentContacts, quotedContacts)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Contact::moment));
    }

}
