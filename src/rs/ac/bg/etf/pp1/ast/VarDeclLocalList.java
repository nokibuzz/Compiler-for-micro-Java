// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class VarDeclLocalList extends VarDeclLocal {

    private VarDeclLocal VarDeclLocal;
    private VarDecl VarDecl;

    public VarDeclLocalList (VarDeclLocal VarDeclLocal, VarDecl VarDecl) {
        this.VarDeclLocal=VarDeclLocal;
        if(VarDeclLocal!=null) VarDeclLocal.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarDeclLocal getVarDeclLocal() {
        return VarDeclLocal;
    }

    public void setVarDeclLocal(VarDeclLocal VarDeclLocal) {
        this.VarDeclLocal=VarDeclLocal;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclLocal!=null) VarDeclLocal.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclLocal!=null) VarDeclLocal.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclLocal!=null) VarDeclLocal.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclLocalList(\n");

        if(VarDeclLocal!=null)
            buffer.append(VarDeclLocal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclLocalList]");
        return buffer.toString();
    }
}
