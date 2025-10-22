// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class ListStatement extends StatementList {

    private StatList StatList;

    public ListStatement (StatList StatList) {
        this.StatList=StatList;
        if(StatList!=null) StatList.setParent(this);
    }

    public StatList getStatList() {
        return StatList;
    }

    public void setStatList(StatList StatList) {
        this.StatList=StatList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatList!=null) StatList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatList!=null) StatList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatList!=null) StatList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListStatement(\n");

        if(StatList!=null)
            buffer.append(StatList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListStatement]");
        return buffer.toString();
    }
}
