// generated with ast extension for cup
// version 0.8
// 1/1/2020 23:36:32


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarations extends LocalVarlDeclList {

    private VarDeclLocal VarDeclLocal;

    public VarDeclarations (VarDeclLocal VarDeclLocal) {
        this.VarDeclLocal=VarDeclLocal;
        if(VarDeclLocal!=null) VarDeclLocal.setParent(this);
    }

    public VarDeclLocal getVarDeclLocal() {
        return VarDeclLocal;
    }

    public void setVarDeclLocal(VarDeclLocal VarDeclLocal) {
        this.VarDeclLocal=VarDeclLocal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclLocal!=null) VarDeclLocal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclLocal!=null) VarDeclLocal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclLocal!=null) VarDeclLocal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarations(\n");

        if(VarDeclLocal!=null)
            buffer.append(VarDeclLocal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarations]");
        return buffer.toString();
    }
}
