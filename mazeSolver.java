public class JamdagniLavanyaA5Q2 {

  public static final char[][] MAZE = {
    "#######################################".toCharArray(),
    "# #         # #     # #   #   # #   # #".toCharArray(),
    "# ##### ### # # # ### ### ### # # ### #".toCharArray(),
    "#         # # # #         #   #       #".toCharArray(),
    "####### ### # ####### # # # # # # # # #".toCharArray(),
    "#     # # # #   # #   # # # #   # # # #".toCharArray(),
    "### ##### ##### # # ### # # # # #######".toCharArray(),
    "# #                   # # # # #       #".toCharArray(),
    "# # ##### ### # ####### # ##### # #####".toCharArray(),
    "#       # # # # #     # #       # # # #".toCharArray(),
    "# # ##### # # # # # ##### ##### # # # #".toCharArray(),
    "# #   #   #   #   # #   #   # # # # # #".toCharArray(),
    "# ########### # # ### ####### # ### # #".toCharArray(),
    "#       #   # # #           # # #     #".toCharArray(),
    "##### ##### ### # ##### # # # # # ### #".toCharArray(),
    "# #     #   # # # #   # # # #     #   #".toCharArray(),
    "# ### ### ### # # # ######### # ### # #".toCharArray(),
    "#       # #     #     #       # #   # #".toCharArray(),
    "# # # ### # # ######### ######### #####".toCharArray(),
    "# # #   #   # #   # # #   #   #       #".toCharArray(),
    "# # # ### # ### # # # # ##### # # ### #".toCharArray(),
    "# # #   # #     #     #       # # #   #".toCharArray(),
    "#######################################".toCharArray()
  };

  public static void main(String[] args) {
    char[][] maze;

    maze = copyMaze(MAZE);
    attemptSolution(maze, new RowCol(21, 1), new RowCol(1, 37), "Original maze, bottom left:");

    maze = copyMaze(MAZE);
    attemptSolution(maze, new RowCol(1, 1), new RowCol(1, 37), "\nOriginal maze, top left:");

    maze = copyMaze(MAZE);
    attemptSolution(maze, new RowCol(21, 35), new RowCol(7, 1), "\nOriginal maze, bottom right:");

    maze = copyMaze(MAZE);
    for (int r = 0; r < maze.length; r++) {
      maze[r][maze[0].length - 5] = '#';
    }
    attemptSolution(maze, new RowCol(21, 1), new RowCol(1, 37), "\nModified maze, bottom left:");

    System.out.println("\nEnd of processing.");
  }

  public static char[][] copyMaze(char[][] maze) {
    char[][] result = new char[maze.length][maze[0].length];
    for (int r = 0; r < maze.length; r++) {
      for (int c = 0; c < maze[r].length; c++) {
        result[r][c] = maze[r][c];
      }
    }
    return result;
  }

  public static void printMaze(char[][] maze) {
    for (int r = 0; r < maze.length; r++) {
      for (int c = 0; c < maze[r].length; c++) {
        System.out.print(maze[r][c]);
      }
      System.out.println();
    }
  }

  public static void attemptSolution(char[][] maze, RowCol start, RowCol goal, String name) {
    System.out.println(name);
    printMaze(maze);

    if (solveMaze(maze, start, goal)) {
      System.out.println("\nSolved maze:");
      printMaze(maze);
    } else {
      System.out.println("\nNo solution.");
    }
  }

  public static boolean solveMaze(char[][] maze, RowCol start, RowCol goal) {
    StackOfRowCol stack = new StackOfRowColLL(), path = new StackOfRowColLL();
    RowCol here, from, check;
    boolean pushed, solved = false;

    stack.push(start); //Pushing start onto the stack

    here = start;
    from = new RowCol(0, 0); 
    while (!stack.isEmpty() && !here.equals(goal)) {
      pushed = false;

      check = new RowCol(here.row() - 1, here.col());
      if (maze[check.row()][check.col()] == ' ' && !from.equals(check)) {
        pushed = true;

        stack.push(check);//Pushing check onto the stack

      }

      check = new RowCol(here.row() + 1, here.col());
      if (maze[check.row()][check.col()] == ' ' && !from.equals(check)) {
        pushed = true;
        stack.push(check);//Pushing check onto the stack

      }

      check = new RowCol(here.row(), here.col() - 1);
      if (maze[check.row()][check.col()] == ' ' && !from.equals(check)) {
        pushed = true;
        stack.push(check);//Pushing check onto the stack

      }

      check = new RowCol(here.row(), here.col() + 1);
      if (maze[check.row()][check.col()] == ' ' && !from.equals(check)) {
        pushed = true;
        stack.push(check);//Pushing check onto the stack

      }

      if (pushed) {
          path.push(here);//Pushing here onto the path

      } else {
        maze[here.row()][here.col()] = 'X';
      }

      from = here;
      here=stack.pop();//Popping here from the stack
    }

    if (here.equals(goal)) {

        while(!path.isEmpty()) {
            RowCol rc = path.pop();
            maze[rc.row()][rc.col()] = '.';//this will place "." at all locations in the path
        }


      solved = true;
    }

    return solved;
  }
}

interface StackOfRowCol {
  void push(RowCol rc);
  RowCol pop();
  boolean isEmpty();
}

class StackOfRowColLL implements StackOfRowCol {//implementation of the stack
    private RowColNode head; //head of the node
    private int size;

    public StackOfRowColLL() {
        head = null; //initialises head as null
        size = 0; //initialises size to zero
    }

    public void push(RowCol rc) {
        RowColNode newNode =new RowColNode(rc,null);//creates a new node
        newNode.setNext(head);//newnode is set to current head
        head=newNode;//head is assigned to the new node
        size++;//increases size by one
    }

    public RowCol pop() {
        RowCol toReturn=null;//initialises toReturn as null
        if(!isEmpty()){//if stack has some val
            toReturn=head.getData();//head is assigned to the next node which removes the orev val from stack
            head=head.getNext();//head is assigned to the next val
            size--;//size is reduced by one
        }
        return toReturn;//returns the removes val
    }

    public boolean isEmpty() {//if the stack is empty

        return size == 0;//the size becomes zero
    }



}

class RowCol {
  private int row, col;
  public RowCol(int r, int c) {
    row = r;
    col = c;
  }
  public int row() {
      return row;
  }
  public int col() {
      return col;
  }
  public boolean equals(RowCol other) {

      return this.row == other.row && this.col == other.col;
  }
  public String toString() {

      return "(" + row + "," + col + ")";
  }
}

class RowColNode {//the rowcol node
    private RowCol data ;
    private RowColNode next;

    public RowColNode(RowCol newData, RowColNode newNext) {
        this.data = newData;
        this.next = newNext;
    }

    public RowCol getData() {

        return data;
    }

    public void setData(RowCol data) {

        this.data = data;
    }

    public RowColNode getNext() {

        return next;
    }

    public void setNext(RowColNode next) {

        this.next = next;
    }
}
