// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class ConstPart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constName;
    private NumCharBool NumCharBool;

    public ConstPart (String constName, NumCharBool NumCharBool) {
        this.constName=constName;
        this.NumCharBool=NumCharBool;
        if(NumCharBool!=null) NumCharBool.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public NumCharBool getNumCharBool() {
        return NumCharBool;
    }

    public void setNumCharBool(NumCharBool NumCharBool) {
        this.NumCharBool=NumCharBool;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NumCharBool!=null) NumCharBool.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NumCharBool!=null) NumCharBool.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NumCharBool!=null) NumCharBool.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstPart(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(NumCharBool!=null)
            buffer.append(NumCharBool.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstPart]");
        return buffer.toString();
    }
}
