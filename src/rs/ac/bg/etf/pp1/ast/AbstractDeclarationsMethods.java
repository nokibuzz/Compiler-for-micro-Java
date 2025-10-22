// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class AbstractDeclarationsMethods extends AbstractMethodDeclarations {

    private MethodOrAbstractDeclList MethodOrAbstractDeclList;

    public AbstractDeclarationsMethods (MethodOrAbstractDeclList MethodOrAbstractDeclList) {
        this.MethodOrAbstractDeclList=MethodOrAbstractDeclList;
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.setParent(this);
    }

    public MethodOrAbstractDeclList getMethodOrAbstractDeclList() {
        return MethodOrAbstractDeclList;
    }

    public void setMethodOrAbstractDeclList(MethodOrAbstractDeclList MethodOrAbstractDeclList) {
        this.MethodOrAbstractDeclList=MethodOrAbstractDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodOrAbstractDeclList!=null) MethodOrAbstractDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractDeclarationsMethods(\n");

        if(MethodOrAbstractDeclList!=null)
            buffer.append(MethodOrAbstractDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractDeclarationsMethods]");
        return buffer.toString();
    }
}
