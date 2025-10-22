// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class AbstractAndMethodDeclarations extends MethodOrAbstractDeclList {

    private MethodOrAbstractDeclList MethodOrAbstractDeclList;
    private MethodOrAbstractDecl MethodOrAbstractDecl;

    public AbstractAndMethodDeclarations (MethodOrAbstractDeclList MethodOrAbstractDeclList, MethodOrAbstractDecl MethodOrAbstractDecl) {
        this.MethodOrAbstractDeclList=MethodOrAbstractDeclList;
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.setParent(this);
        this.MethodOrAbstractDecl=MethodOrAbstractDecl;
        if(MethodOrAbstractDecl!=null) MethodOrAbstractDecl.setParent(this);
    }

    public MethodOrAbstractDeclList getMethodOrAbstractDeclList() {
        return MethodOrAbstractDeclList;
    }

    public void setMethodOrAbstractDeclList(MethodOrAbstractDeclList MethodOrAbstractDeclList) {
        this.MethodOrAbstractDeclList=MethodOrAbstractDeclList;
    }

    public MethodOrAbstractDecl getMethodOrAbstractDecl() {
        return MethodOrAbstractDecl;
    }

    public void setMethodOrAbstractDecl(MethodOrAbstractDecl MethodOrAbstractDecl) {
        this.MethodOrAbstractDecl=MethodOrAbstractDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.accept(visitor);
        if(MethodOrAbstractDecl!=null) MethodOrAbstractDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.traverseTopDown(visitor);
        if(MethodOrAbstractDecl!=null) MethodOrAbstractDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.traverseBottomUp(visitor);
        if(MethodOrAbstractDecl!=null) MethodOrAbstractDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractAndMethodDeclarations(\n");

        if(MethodOrAbstractDeclList!=null)
            buffer.append(MethodOrAbstractDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodOrAbstractDecl!=null)
            buffer.append(MethodOrAbstractDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractAndMethodDeclarations]");
        return buffer.toString();
    }
}
