package graph;

import corona_5g.analyze.Contacts.CommentContact;
import corona_5g.analyze.Contacts.Contact;
import corona_5g.analyze.Contacts.QuoteContact;
import corona_5g.analyze.Contacts.RetweetContact;
import graph.Contacts.CommentContact;
import graph.Contacts.Contact;
import graph.Contacts.QuoteContact;
import graph.Contacts.RetweetContact;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparing;

/**
 * Edges count the number of tweets, retweets and comments
 * between users. Here, each of this contacts is explicitly
 * stored in a contact object.
 */
public class Edge extends DefaultWeightedEdge implements Cloneable {
    private int retweetContacts = 0;
    private int quotedContacts = 0;
    private int commentContact = 0;

    public Set<Contact<?>> contacts = new TreeSet<>(comparing(Contact::moment));

    Edge(Contact<?> contact) {
        addContact(contact);
    }

    Edge addContact(Contact<?> contact) {
        if (contact instanceof RetweetContact) retweetContacts++;
        else if (contact instanceof QuoteContact) quotedContacts++;
        else if (contact instanceof CommentContact) commentContact++;
        contacts.add(contact);
        return this;
    }

    public boolean isAfter(LocalDateTime date) {
        for (var contact : contacts)
            if (contact.moment().isAfter(date))
                return true;
        return false;
    }

    public boolean isBefore(LocalDateTime date) {
        for (var contact : contacts)
            if (contact.moment().isBefore(date))
                return true;
        return false;
    }

    public boolean containsRetweetContact() { return retweetContacts > 0; }
    public boolean containsCommentContact() { return commentContact > 0; }
    public boolean containsQuoteContact() { return quotedContacts > 0; }


    @Override
    protected double getWeight() {
        return contacts.size();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        contacts.forEach(sb::append);
        return sb.toString();
    }

    @Override
    public Edge clone() {
        var newEdge = (Edge) super.clone();
        newEdge.contacts.addAll(contacts);
        return newEdge;
    }
}
