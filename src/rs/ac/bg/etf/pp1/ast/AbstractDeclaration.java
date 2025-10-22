// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class AbstractDeclaration extends Decl {

    private AbstractDecl AbstractDecl;

    public AbstractDeclaration (AbstractDecl AbstractDecl) {
        this.AbstractDecl=AbstractDecl;
        if(AbstractDecl!=null) AbstractDecl.setParent(this);
    }

    public AbstractDecl getAbstractDecl() {
        return AbstractDecl;
    }

    public void setAbstractDecl(AbstractDecl AbstractDecl) {
        this.AbstractDecl=AbstractDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AbstractDecl!=null) AbstractDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AbstractDecl!=null) AbstractDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AbstractDecl!=null) AbstractDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractDeclaration(\n");

        if(AbstractDecl!=null)
            buffer.append(AbstractDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractDeclaration]");
        return buffer.toString();
    }
}
