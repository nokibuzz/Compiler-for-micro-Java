// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class AbstractDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String className;
    private ExtendsType ExtendsType;
    private LocalVarlDeclList LocalVarlDeclList;
    private AbstractMethodDeclarations AbstractMethodDeclarations;

    public AbstractDecl (String className, ExtendsType ExtendsType, LocalVarlDeclList LocalVarlDeclList, AbstractMethodDeclarations AbstractMethodDeclarations) {
        this.className=className;
        this.ExtendsType=ExtendsType;
        if(ExtendsType!=null) ExtendsType.setParent(this);
        this.LocalVarlDeclList=LocalVarlDeclList;
        if(LocalVarlDeclList!=null) LocalVarlDeclList.setParent(this);
        this.AbstractMethodDeclarations=AbstractMethodDeclarations;
        if(AbstractMethodDeclarations!=null) AbstractMethodDeclarations.setParent(this);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
    }

    public ExtendsType getExtendsType() {
        return ExtendsType;
    }

    public void setExtendsType(ExtendsType ExtendsType) {
        this.ExtendsType=ExtendsType;
    }

    public LocalVarlDeclList getLocalVarlDeclList() {
        return LocalVarlDeclList;
    }

    public void setLocalVarlDeclList(LocalVarlDeclList LocalVarlDeclList) {
        this.LocalVarlDeclList=LocalVarlDeclList;
    }

    public AbstractMethodDeclarations getAbstractMethodDeclarations() {
        return AbstractMethodDeclarations;
    }

    public void setAbstractMethodDeclarations(AbstractMethodDeclarations AbstractMethodDeclarations) {
        this.AbstractMethodDeclarations=AbstractMethodDeclarations;
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
        if(ExtendsType!=null) ExtendsType.accept(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.accept(visitor);
        if(AbstractMethodDeclarations!=null) AbstractMethodDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsType!=null) ExtendsType.traverseTopDown(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.traverseTopDown(visitor);
        if(AbstractMethodDeclarations!=null) AbstractMethodDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsType!=null) ExtendsType.traverseBottomUp(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.traverseBottomUp(visitor);
        if(AbstractMethodDeclarations!=null) AbstractMethodDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AbstractDecl(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        if(ExtendsType!=null)
            buffer.append(ExtendsType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarlDeclList!=null)
            buffer.append(LocalVarlDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AbstractMethodDeclarations!=null)
            buffer.append(AbstractMethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AbstractDecl]");
        return buffer.toString();
    }
}
