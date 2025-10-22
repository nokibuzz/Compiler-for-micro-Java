// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class ForStatement extends Matched {

    private For For;
    private DesignatorStatements DesignatorStatements;
    private Conditions Conditions;
    private DesignatorStatements DesignatorStatements1;
    private Matched Matched;

    public ForStatement (For For, DesignatorStatements DesignatorStatements, Conditions Conditions, DesignatorStatements DesignatorStatements1, Matched Matched) {
        this.For=For;
        if(For!=null) For.setParent(this);
        this.DesignatorStatements=DesignatorStatements;
        if(DesignatorStatements!=null) DesignatorStatements.setParent(this);
        this.Conditions=Conditions;
        if(Conditions!=null) Conditions.setParent(this);
        this.DesignatorStatements1=DesignatorStatements1;
        if(DesignatorStatements1!=null) DesignatorStatements1.setParent(this);
        this.Matched=Matched;
        if(Matched!=null) Matched.setParent(this);
    }

    public For getFor() {
        return For;
    }

    public void setFor(For For) {
        this.For=For;
    }

    public DesignatorStatements getDesignatorStatements() {
        return DesignatorStatements;
    }

    public void setDesignatorStatements(DesignatorStatements DesignatorStatements) {
        this.DesignatorStatements=DesignatorStatements;
    }

    public Conditions getConditions() {
        return Conditions;
    }

    public void setConditions(Conditions Conditions) {
        this.Conditions=Conditions;
    }

    public DesignatorStatements getDesignatorStatements1() {
        return DesignatorStatements1;
    }

    public void setDesignatorStatements1(DesignatorStatements DesignatorStatements1) {
        this.DesignatorStatements1=DesignatorStatements1;
    }

    public Matched getMatched() {
        return Matched;
    }

    public void setMatched(Matched Matched) {
        this.Matched=Matched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(For!=null) For.accept(visitor);
        if(DesignatorStatements!=null) DesignatorStatements.accept(visitor);
        if(Conditions!=null) Conditions.accept(visitor);
        if(DesignatorStatements1!=null) DesignatorStatements1.accept(visitor);
        if(Matched!=null) Matched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(For!=null) For.traverseTopDown(visitor);
        if(DesignatorStatements!=null) DesignatorStatements.traverseTopDown(visitor);
        if(Conditions!=null) Conditions.traverseTopDown(visitor);
        if(DesignatorStatements1!=null) DesignatorStatements1.traverseTopDown(visitor);
        if(Matched!=null) Matched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(For!=null) For.traverseBottomUp(visitor);
        if(DesignatorStatements!=null) DesignatorStatements.traverseBottomUp(visitor);
        if(Conditions!=null) Conditions.traverseBottomUp(visitor);
        if(DesignatorStatements1!=null) DesignatorStatements1.traverseBottomUp(visitor);
        if(Matched!=null) Matched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStatement(\n");

        if(For!=null)
            buffer.append(For.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatements!=null)
            buffer.append(DesignatorStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Conditions!=null)
            buffer.append(Conditions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatements1!=null)
            buffer.append(DesignatorStatements1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Matched!=null)
            buffer.append(Matched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStatement]");
        return buffer.toString();
    }
}
