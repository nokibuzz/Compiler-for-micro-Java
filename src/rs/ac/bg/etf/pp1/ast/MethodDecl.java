// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private MethodReturnType MethodReturnType;
    private FormParameters FormParameters;
    private LocalVarlDeclList LocalVarlDeclList;
    private StatementList StatementList;

    public MethodDecl (MethodReturnType MethodReturnType, FormParameters FormParameters, LocalVarlDeclList LocalVarlDeclList, StatementList StatementList) {
        this.MethodReturnType=MethodReturnType;
        if(MethodReturnType!=null) MethodReturnType.setParent(this);
        this.FormParameters=FormParameters;
        if(FormParameters!=null) FormParameters.setParent(this);
        this.LocalVarlDeclList=LocalVarlDeclList;
        if(LocalVarlDeclList!=null) LocalVarlDeclList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodReturnType getMethodReturnType() {
        return MethodReturnType;
    }

    public void setMethodReturnType(MethodReturnType MethodReturnType) {
        this.MethodReturnType=MethodReturnType;
    }

    public FormParameters getFormParameters() {
        return FormParameters;
    }

    public void setFormParameters(FormParameters FormParameters) {
        this.FormParameters=FormParameters;
    }

    public LocalVarlDeclList getLocalVarlDeclList() {
        return LocalVarlDeclList;
    }

    public void setLocalVarlDeclList(LocalVarlDeclList LocalVarlDeclList) {
        this.LocalVarlDeclList=LocalVarlDeclList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(MethodReturnType!=null) MethodReturnType.accept(visitor);
        if(FormParameters!=null) FormParameters.accept(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodReturnType!=null) MethodReturnType.traverseTopDown(visitor);
        if(FormParameters!=null) FormParameters.traverseTopDown(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodReturnType!=null) MethodReturnType.traverseBottomUp(visitor);
        if(FormParameters!=null) FormParameters.traverseBottomUp(visitor);
        if(LocalVarlDeclList!=null) LocalVarlDeclList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodReturnType!=null)
            buffer.append(MethodReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParameters!=null)
            buffer.append(FormParameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVarlDeclList!=null)
            buffer.append(LocalVarlDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
