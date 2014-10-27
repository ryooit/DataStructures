package Cons.asg4;

/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          02 Oct 09; 12 Feb 10; 04 Oct 12
 *          
 * @name Intae Ryoo
 * @eid ir5542
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

    // convert a list to a string for printing
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    // iterative destructive merge using compareTo
public static Cons dmerj (Cons x, Cons y) {
  if ( x == null ) return y;
   else if ( y == null ) return x;
   else { Cons front = x;
          if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
             x = rest(x);
            else { front = y;
                   y = rest(y); };
          Cons end = front;
          while ( x != null )
            { if ( y == null ||
                   ((Comparable) first(x)).compareTo(first(y)) < 0)
                 { setrest(end, x);
                   x = rest(x); }
               else { setrest(end, y);
                      y = rest(y); };
              end = rest(end); }
          setrest(end, y);
          return front; } }

public static Cons midpoint (Cons lst) {
  Cons current = lst;
  Cons prev = current;
  while ( lst != null && rest(lst) != null) {
    lst = rest(rest(lst));
    prev = current;
    current = rest(current); };
  return prev; }

    // Destructive merge sort of a linked list, Ascending order.
    // Assumes that each list element implements the Comparable interface.
    // This function will rearrange the order (but not location)
    // of list elements.  Therefore, you must save the result of
    // this function as the pointer to the new head of the list, e.g.
    //    mylist = llmergesort(mylist);
public static Cons llmergesort (Cons lst) {
  if ( lst == null || rest(lst) == null)
     return lst;
   else { Cons mid = midpoint(lst);
          Cons half = rest(mid);
          setrest(mid, null);
          return dmerj( llmergesort(lst),
                        llmergesort(half)); } }


    // ****** your code starts here ******
    // add other functions as you wish.

public static Cons union (Cons x, Cons y) {
	x = llmergesort(x);
	y = llmergesort(y);
	x = mergeunion(x, y);
	return dmerj(x,y);
}
	// following is a helper function for union
public static Cons mergeunion (Cons x, Cons y){
	if (x == null || y == null)
		return null;
	else if (first(x).equals(first(y)))
		return mergeunion (rest(x), rest(y));
	else if ( ((Comparable) first(x)).compareTo(first(y)) < 0)
		return cons(first(x), mergeunion(rest(x), y));
    else 
    	return cons(first(y), mergeunion(x, rest(y)));
}

public static Cons setDifference (Cons x, Cons y) {
	x = llmergesort(x);
	y = llmergesort(y);
	return mergediff(x, y);	
}

    // following is a helper function for setDifference
public static Cons mergediff (Cons x, Cons y) {
	if ( x== null || y == null )
		return null;
	else if (first(x).equals(first(y)))
		return mergediff(rest(x), rest(y));
	else if ( ((Comparable) first(x)).compareTo(first(y)) < 0 )
		return cons(first(x), mergediff(rest(x), y));
	else
		return cons(first(y), mergediff(x, rest(y)));
	
}

public static Cons bank (Cons accounts, Cons updates) {
	Cons newAccount = null;
	updates = llmergesort(updates);
	while (updates != null) {
		Account account = (Account) first(accounts);
		Account update = (Account) first(updates);
		int compare = update.name().compareTo(account.name());
		if (compare > 0) {
			newAccount = cons(account, newAccount);
			accounts = rest(accounts);
		}
		else if (compare < 0) {
			if (update.amount() >= 0){
				System.out.println("(" + update.name() + ") is successfully added with the balance of $" + update.amount() + ".");
				newAccount = cons(update, newAccount);
			}
			else {
				System.out.println("(" + update.name() + ") is not added with balance of $" + update.amount() + " because of the negative balance.");
			}
			updates = rest(updates);
		}
		else {
			int balance = update.amount() + account.amount();
			if (balance < 0) {
				System.out.println("Final amount of account (" + update.name() + ") is $" + balance + ". Subtract $30 for an overdraft fee.");
				balance = balance - 30;
			}
			setfirst(accounts, new Account(account.name(), balance));
			updates = rest(updates);
		}
	}
	while (accounts != null) {
		newAccount = cons(first(accounts), newAccount);
		accounts = rest(accounts);
	}
	return reverse(newAccount);
}

public static Cons reverse (Cons list) {
	Cons result = null;

	while (list != null) {
		result = cons(first(list), result);
		list = rest(list);
	}
	return result;
}

public static String[] mergearr (String[] x, String[] y) {
	String[] arr = new String[x.length + y.length];
	int arrIndex = 0, xIndex = 0, yIndex = 0;

	while (xIndex < x.length && yIndex < y.length) {
		if (x[xIndex].compareTo(y[yIndex]) < 0)
			arr[arrIndex++] = x[xIndex++];
		else if (x[xIndex].compareTo(y[yIndex]) > 0)
			arr[arrIndex++] = y[yIndex++];
		else {
			arr[arrIndex++] = x[xIndex++];
			arr[arrIndex++] = y[yIndex++];
		}
	}
	while (xIndex < x.length) {
		arr[arrIndex++] = x[xIndex++];
	}
	while (yIndex < y.length){
		arr[arrIndex++] = y[yIndex++];
	}
	return arr;
}

public static boolean markup (Cons text) {
	Cons stack = null;
	int position = 0;
	
	while (text != null) {
		String cursor = (String) first(text);

		if (cursor.length() > 0 && cursor.charAt(0) == '<') {
			if (cursor.charAt(1) == '/') {
				if (!cursor.substring(2).equals(first(stack))){
					if (first(stack) == null)
						System.out.println("Unexpected " + cursor + " at position " + position + ".");
					else
						System.out.println("Expected <" + first(stack) + " tag at position " + position + ".");
					return false;
				}
				stack = rest(stack);
			}
			else
				stack = cons(cursor.substring(1), stack);
		}
		text = rest(text);
		position++;
	}
	if (first(stack) != null){
		System.out.println("Unexpected <" + first(stack) + " at position " + position + ".");
		return false;
	}
	else
		return true;
}
    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons set1 = list("d", "b", "c", "a");
        Cons set2 = list("f", "d", "b", "g", "h");
        System.out.println("set1 = " + Cons.toString(set1));
        System.out.println("set2 = " + Cons.toString(set2));
        System.out.println("union = " + Cons.toString(union(set1, set2)));

        Cons set3 = list("d", "b", "c", "a");
        Cons set4 = list("f", "d", "b", "g", "h");
        System.out.println("set3 = " + Cons.toString(set3));
        System.out.println("set4 = " + Cons.toString(set4));
        System.out.println("difference = " +
                           Cons.toString(setDifference(set3, set4)));

        Cons accounts = list(
               new Account("Arbiter", new Integer(498)),
               new Account("Flintstone", new Integer(102)),
               new Account("Foonly", new Integer(123)),
               new Account("Kenobi", new Integer(373)),
               new Account("Rubble", new Integer(514)),
               new Account("Tirebiter", new Integer(752)),
               new Account("Vader", new Integer(1024)) );

        Cons updates = list(
               new Account("Foonly", new Integer(100)),
               new Account("Flintstone", new Integer(-10)),
               new Account("Arbiter", new Integer(-600)),
               new Account("Garble", new Integer(-100)),
               new Account("Rabble", new Integer(100)),
               new Account("Flintstone", new Integer(-20)),
               new Account("Foonly", new Integer(10)),
               new Account("Tirebiter", new Integer(-200)),
               new Account("Flintstone", new Integer(10)),
               new Account("Flintstone", new Integer(-120))  );
        System.out.println("accounts = " + accounts.toString());
        System.out.println("updates = " + updates.toString());
        Cons newaccounts = bank(accounts, updates);
        System.out.println("result = " + newaccounts.toString());

        String[] arra = {"a", "big", "dog", "hippo"};
        String[] arrb = {"canary", "cat", "fox", "turtle"};
        String[] resarr = mergearr(arra, arrb);
        for ( int i = 0; i < resarr.length; i++ )
            System.out.println(resarr[i]);

        Cons xmla = list( "<TT>", "foo", "</TT>");
        Cons xmlb = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</TR>", "</TABLE>" );
        Cons xmlc = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "</TR>",
                          "<TR>", "<TD>", "fum", "</TD>", "<TD>",
                          "baz", "</TD>", "</WHAT>", "</TABLE>" );
        Cons xmld = list( "<TABLE>", "<TR>", "<TD>", "foo", "</TD>",
                          "<TD>", "bar", "</TD>", "", "</TR>",
                          "</TABLE>", "</NOW>" );
        Cons xmle = list( "<THIS>", "<CANT>", "<BE>", "foo", "<RIGHT>" );
        Cons xmlf = list( "<CATALOG>",
                          "<CD>",
                          "<TITLE>", "Empire", "Burlesque", "</TITLE>",
                          "<ARTIST>", "Bob", "Dylan", "</ARTIST>",
                          "<COUNTRY>", "USA", "</COUNTRY>",
                          "<COMPANY>", "Columbia", "</COMPANY>",
                          "<PRICE>", "10.90", "</PRICE>",
                          "<YEAR>", "1985", "</YEAR>",
                          "</CD>",
                          "<CD>",
                          "<TITLE>", "Hide", "your", "heart", "</TITLE>",
                          "<ARTIST>", "Bonnie", "Tyler", "</ARTIST>",
                          "<COUNTRY>", "UK", "</COUNTRY>",
                          "<COMPANY>", "CBS", "Records", "</COMPANY>",
                          "<PRICE>", "9.90", "</PRICE>",
                          "<YEAR>", "1988", "</YEAR>",
                          "</CD>", "</CATALOG>");
        System.out.println("xmla = " + xmla.toString());
        System.out.println("result = " + markup(xmla));
        System.out.println("xmlb = " + xmlb.toString());
        System.out.println("result = " + markup(xmlb));
        System.out.println("xmlc = " + xmlc.toString());
        System.out.println("result = " + markup(xmlc));
        System.out.println("xmld = " + xmld.toString());
        System.out.println("result = " + markup(xmld));
        System.out.println("xmle = " + xmle.toString());
        System.out.println("result = " + markup(xmle));
        System.out.println("xmlf = " + xmlf.toString());
        System.out.println("result = " + markup(xmlf));

      }

}