public class Location {
    
    private int row;
    private int col;
    
    public Location(int r, int c) {
        row = r;
        col = c;
    }
    
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    @Override
    public int hashCode(){
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location){
            Location otherLoc = (Location)obj;
            return row == otherLoc.getRow() && col == otherLoc.getCol();
        }
        return false;
    }
    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
    
}