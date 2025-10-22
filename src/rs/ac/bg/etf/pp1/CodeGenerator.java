package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(PrintNoNumConstStatement printNoNumConstStatement) {
		int kind = printNoNumConstStatement.getExpr().struct.getKind();
		if (kind == Struct.Array) {
			int arrKind = printNoNumConstStatement.getExpr().struct.getElemType().getKind();
			if (arrKind == Struct.Int) {
				Code.loadConst(4);
				Code.put(Code.print);
			} else if (arrKind == Struct.Char) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else {
				Code.loadConst(4);
				Code.put(Code.print);
			}
		} else {
			if (kind == Struct.Int) {
				Code.loadConst(4);
				Code.put(Code.print);
			} else if (kind == Struct.Char) {
				Code.loadConst(1);
				Code.put(Code.bprint);
			} else {
				Code.loadConst(4);
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(PrintNumConstStatement printNumConstStatement) {
		int kind = printNumConstStatement.getExpr().struct.getKind();
		if (kind == Struct.Array) {
			int arrKind = printNumConstStatement.getExpr().struct.getElemType().getKind();
			if (arrKind == Struct.Int) {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.print);
			} else if (arrKind == Struct.Char) {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.bprint);
			} else {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.print);
			}
		} else {
			if (kind == Struct.Int) {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.print);
			} else if (kind == Struct.Char) {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.bprint);
			} else {
				Obj number = Tab.insert(Obj.Con, "", Tab.intType);
				number.setAdr(printNumConstStatement.getN2());
				Code.load(number);
				Code.put(Code.print);
			}
		}
	}
	
	public void visit(ReadStatement readStatement) {
        if (readStatement.getDesignator().getDesignatorList().obj.getType() == Tab.charType) {
            Code.put(Code.bread);
        } else {
            Code.put(Code.read);
        }
        Code.store(readStatement.getDesignator().getDesignatorList().obj);
    }
	
	public void visit(MinusExpr expr) {
		Code.put(Code.neg);
	}
	
	public void visit(IntRef intRef) {
		Obj con = Tab.insert(Obj.Con, "", intRef.struct);
//		con.setLevel(0);
		con.setAdr(intRef.getN1());
		
		Code.load(con);
	}
	
	public void visit(CharRef charRef) {
		Obj con = Tab.insert(Obj.Con, "", charRef.struct);
//		con.setLevel(0);
		con.setAdr(charRef.getC1());
		
		Code.load(con);
	}
	
	public void visit(BoolRef boolRef) {
		Obj con = Tab.insert(Obj.Con, "", boolRef.struct);
//		con.setLevel(0);
		con.setAdr(boolRef.getB1().equals("true") ? 1 : 0);
		
		Code.load(con);
	}
	
	/////////////////////////////////////////////////////
	public void visit(OperatorNewArray operatorNewArray) {
		Code.put(Code.newarray);
		if (operatorNewArray.getType().struct.getKind() == Struct.Char) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}
	
	public void visit(MethodVoid methodVoid) {
		if (methodVoid.getMethodName().equals("main")) {
			mainPc = Code.pc;
		}
		methodVoid.obj.setAdr(Code.pc);
		
		//Collect arguments and local variables
		SyntaxNode methodNode = methodVoid.getParent();
		
		FormParamCounter formParamCounter = new FormParamCounter();
		methodNode.traverseTopDown(formParamCounter);
		
		VarCounter varCounter = new VarCounter();
		methodNode.traverseTopDown(varCounter);
		
		//Generate entry
		Code.put(Code.enter);
		Code.put(formParamCounter.getCount());
		Code.put(formParamCounter.getCount() + varCounter.getCount());
	}
	
	public void visit(MethodType methodType) {
		methodType.obj.setAdr(Code.pc);
		
		//Collect arguments and local variables
		SyntaxNode methodNode = methodType.getParent();
		
		FormParamCounter formParamCounter = new FormParamCounter();
		methodNode.traverseTopDown(formParamCounter);
		
		VarCounter varCounter = new VarCounter();
		methodNode.traverseTopDown(varCounter);
		
		//Generate entry
		Code.put(Code.enter);
		Code.put(formParamCounter.getCount());
		Code.put(formParamCounter.getCount() + varCounter.getCount());
	}
	
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	// assignment za char prepraviti
	public void visit(Assignment assignment) {
		if (assignment.getDesignator().getDesignatorList() instanceof SingleDesignator) {
			Code.store(assignment.getDesignator().getDesignatorList().obj);
		} else {
			if (assignment.getDesignator().getDesignatorList().obj.getType().getElemType() == Tab.intType)
				Code.put(Code.astore);
			else
				Code.put(Code.bastore);
		}
	}
	
//	public void visit(Designator designator) {
//		SyntaxNode parent = designator.getParent();
//		
//		if (
//				Assignment.class != parent.getClass() && ProcRef.class != parent.getClass() &&
//				FuncRef.class != parent.getClass()
//				//&& BoolRef.class != parent.getClass() 
//				//&& ParenthesisExpr.class != parent.getClass()
//				&& ProcCallStatement.class != parent.getClass() && FuncCallStatement.class != parent.getClass()
//				) {
//
//			Code.load(designator.getDesignatorList().obj);
//		}
//	}
	
	public void visit(ArrayDesignator designator) {
		if (!(designator.getParent().getParent() instanceof DesignatorStatement) &&
			!(designator.getParent().getParent() instanceof ReadStatement) &&
			!(designator.getParent().getParent() instanceof ProcRef)) {
			
			if (designator.getDesignatorList().obj.getType().getElemType() == Tab.intType) 
				Code.put(Code.aload);
			else
				Code.put(Code.baload);
		}
	}
	
	public void visit(SingleDesignator designator) {
		if (!(designator.getParent().getParent() instanceof DesignatorStatement) &&
			!(designator.getParent().getParent() instanceof ReadStatement) &&
			!(designator.getParent().getParent() instanceof ProcRef)
			) 
				Code.load(designator.obj);
	}
	
	public void visit(FuncCallStatement funcRef) {
		Obj funcObj = funcRef.getDesignator().getDesignatorList().obj;
		int offset = funcObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(ProcCallStatement procRef) {
		Obj funcObj = procRef.getDesignator().getDesignatorList().obj;
		int offset = funcObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (procRef.getDesignator().getDesignatorList().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(ProcRef procRef) {
		Obj temp = procRef.getDesignator().getDesignatorList().obj;
		Code.put(Code.call);
		Code.put2( temp.getAdr() - Code.pc + 1);
	}
	
	public void visit(ReturnExprStatement exprStatement) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(ReturnNoExprStatement exprStatement) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(AddOpExpr addOpExpr) {
		if (addOpExpr.getAddop() instanceof PlusOp) {
			Code.put(Code.add);
		}
		if (addOpExpr.getAddop() instanceof MinusOp) {
			Code.put(Code.sub);
		}
	}
	
	public void visit(ListTerm listTerm) {
		if (listTerm.getMulop() instanceof MulOper) {
			Code.put(Code.mul);
		}
		if (listTerm.getMulop() instanceof DivOper) {
			Code.put(Code.div);
		}
		if (listTerm.getMulop() instanceof ModOper) {
			Code.put(Code.rem);
		}
	}
	
	public void visit(PlusPlusStatement statement) {
		Obj obj = statement.getDesignator().getDesignatorList().obj; 
		if (obj.getKind() != Obj.Elem) {
			Code.load(obj);
		} else {
			Code.put(Code.dup2);
			Code.put(Code.aload);
		}
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(obj);
	}
	
	public void visit(MinusMinusStatement statement) {
		Obj obj = statement.getDesignator().getDesignatorList().obj; 
		if (obj.getKind() != Obj.Elem) {
			Code.load(obj);
		} else {
			Code.put(Code.dup2);
			Code.put(Code.aload);
		}
		Code.put(Code.const_1);
		Code.put(Code.sub);
		Code.store(obj);
	}
}
