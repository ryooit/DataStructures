/**********************
 *
 * @author Intae Ryoo
 * @eid ir5542
 *
 **********************/

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }

    // make a new Cons and put the arguments into it
    // add one new thing to the front of an existing list
    // cons("a", list("b", "c"))  =  (a b c)
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }

    // test whether argument is a Cons
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }

    // first thing in a list:    first(list("a", "b", "c")) = "a"
    // safe, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }

    // rest of a list after the first thing:
    //    rest(list("a", "b", "c")) = (b c)
    // safe, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }

    // second thing in a list:    second(list("a", "b", "c")) = "b"
    public static Object second (Cons x) { return first(rest(x)); }

    // third thing in a list:    third(list("a", "b", "c")) = "c"
    public static Object third (Cons x) { return first(rest(rest(x))); }

    // destructively replace the first
    public static void setfirst (Cons x, Object i) { x.car = i; }

    // destructively replace the rest
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }

    // make a list of things:   list("a", "b", "c") = (a b c)
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

    // ****** your code starts here ******

    // Sum of squares of integers from 1..n
    public static int sumsq (int n) {
    	int sum = 0;
    	return ((n==0) ? sum : n + sumsq(n-1));
    }

    // Addition using Peano arithmetic
    public static int peanoplus(int x, int y) {
    	return ((y==0)? x : peanoplus(++x,--y));
    }

    // Multiplication using Peano arithmetic
    public static int peanotimes(int x, int y) {
    	return ((y<=1)? ((y==0 || x==0)? 0 : x) : peanoplus(peanotimes(x,--y),x));
    }

    // n choose k: distinct subsets of k items chosen from n items
    public static int choose(int n, int k) { 
    	return (int)chooseb(n, k, 1, 1);
    }
    
    //chooseb: auxiliary function of choose
    public static long chooseb(int n, int k, long up, long down){
    	return ((k == 0) ? (up / down) : chooseb(n-1, k-1, n*up, k*down)); 
    }

    // Add up a list of Integer
    // iterative version, using while
public static int sumlist (Cons lst) {
  int sum = 0;
   while ( lst != null ) {
      sum += (Integer) first(lst);   // cast since first() can be Object
    lst = rest(lst); }
  return sum; }

    // second iterative version, using for
public static int sumlistb (Cons arg) {
  int sum = 0;
  for (Cons lst = arg ; lst != null; lst = rest(lst) )
    sum += (Integer) first(lst);   // cast since first() can be Object
  return sum; }

    // recursive version
public static int sumlistr (Cons lst) {
	int sum = 0;
	if(lst == null)
		return sum;
	else
		return (Integer)first(lst) + sumlistr(rest(lst));
	
}

    // tail recursive version
public static int sumlisttr (Cons lst) {
	return sumlisttrb(lst, 0);
}

	//sumlisttrb: auxiliary function of sumlisttr
public static int sumlisttrb (Cons lst, int sum) {
	if(lst == null)
		return sum;
	else
		return sumlisttrb(rest(lst), sum + (Integer)first(lst));
}

    // Sum of squared differences of elements of two lists
    // iterative version
public static int sumsqdiff (Cons lst, Cons lstb) {
	int sum = 0;
	while(lst != null){
		sum += square((Integer)first(lst) - (Integer)first(lstb));
		lst = rest(lst);
		lstb = rest(lstb);
	}
	return sum;
}

    // recursive version
public static int sumsqdiffr (Cons lst, Cons lstb) {
	int sum = 0;
	if(lst == null)
		return sum;
	else
		return sum + square((Integer)first(lst) - (Integer)first(lstb)) + sumsqdiffr(rest(lst), rest(lstb));
}

    // tail recursive version
public static int sumsqdifftr (Cons lst, Cons lstb) {
	return sumsqdifftrb(lst, lstb, 0);
}
	//sumsqdifftrb: auxiliary function of sumsqdifftr
public static int sumsqdifftrb (Cons lst, Cons lstb, int sum){
	if(lst == null)
		return sum;
	else
		return sumsqdifftrb(rest(lst), rest(lstb), sum + square((Integer)first(lst) - (Integer)first(lstb)));
}

    // Find the maximum value in a list of Integer
    // iterative version
public static int maxlist (Cons lst) {
	int tmp = (Integer)first(lst);
	while(rest(lst) != null){
		tmp = (tmp < (Integer)first(lst)) ? (Integer)first(lst) : tmp;
		lst = rest(lst);
	}
		return tmp;
}

    // recursive version
public static int maxlistr (Cons lst) {
	int tmp = Integer.MIN_VALUE;
	if(lst == null)
		return tmp;
	else
		return ((tmp = maxlistr(rest(lst))) < (Integer) first(lst)) ? (Integer)first(lst) : tmp;
}

    // tail recursive version
public static int maxlisttr (Cons lst) {
	return maxlisttrb(lst, Integer.MIN_VALUE);
}
	//maxlisttrb: auxiliary function of maxlisttr
public static int maxlisttrb(Cons lst, int tmp){
	if (lst == null)
		return tmp;
	else
		return maxlisttrb(rest(lst), (tmp < (Integer)first(lst)) ? (Integer) first(lst) : tmp);
}

    // Make a list of Binomial coefficients
    // binomial(2) = (1 2 1)
public static Cons binomial(int n) {
    Cons lst = list(Integer.valueOf(1));
    return binomialb(lst, n);
}
	// binomialb: the auxiliary function of binomial
public static Cons binomialb(Cons lst, int n){
    Cons lstb = list(Integer.valueOf(1));
    if (n ==0)
    	return lst;
    else
    	return binomialb(cons(Integer.valueOf(1), binomialbb(lst, lstb)), n-1);
}
	//binomialbb: the auxiliary function of binomialb
public static Cons binomialbb(Cons lst, Cons lstb){
	if (rest(lst) == null)
		return lstb;
	else
		return binomialbb(rest(lst), cons((Integer) first(lst) + (Integer) first(rest(lst)), lstb));
}
    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        System.out.println("sumsq(5) = " + sumsq(5));

        System.out.println("peanoplus(3, 5) = " + peanoplus(3, 5));
        System.out.println("peanotimes(3, 5) = " + peanotimes(3, 5));
        System.out.println("peanotimes(30, 30) = " + peanotimes(30, 30));

        System.out.println("choose 5 3 = " + choose(5, 3));
        System.out.println("choose 100 3 = " + choose(100, 3));
        System.out.println("choose 20 10 = " + choose(20, 10));
        System.out.println("choose 100 5 = " + choose(100, 5));
        for (int i = 0; i <= 4; i++)
          System.out.println("choose 4 " + i + " = " + choose(4, i));

        Cons mylist = list(Integer.valueOf(3), Integer.valueOf(4),
                           Integer.valueOf(8), Integer.valueOf(2));
        Cons mylistb = list(Integer.valueOf(2), Integer.valueOf(1),
                           Integer.valueOf(6), Integer.valueOf(5));

        System.out.println("mylist = " + mylist);

        System.out.println("sumlist = " + sumlist(mylist));
        System.out.println("sumlistb = " + sumlistb(mylist));
        System.out.println("sumlistr = " + sumlistr(mylist));
        System.out.println("sumlisttr = " + sumlisttr(mylist));

        System.out.println("mylistb = " + mylistb);

        System.out.println("sumsqdiff = " + sumsqdiff(mylist, mylistb));
        System.out.println("sumsqdiffr = " + sumsqdiffr(mylist, mylistb));

        System.out.println("sumsqdifftr = " + sumsqdifftr(mylist, mylistb));

        System.out.println("maxlist " + mylist + " = " + maxlist(mylist));
        System.out.println("maxlistr = " + maxlistr(mylist));
        System.out.println("maxlisttr = " + maxlisttr(mylist));

        System.out.println("binomial(4) = " + binomial(4));
        System.out.println("binomial(20) = " + binomial(20));
      }

}
