package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.AddOpExpr;
import rs.ac.bg.etf.pp1.ast.ArrayDesignator;
import rs.ac.bg.etf.pp1.ast.ArrayParam;
import rs.ac.bg.etf.pp1.ast.Assignment;
import rs.ac.bg.etf.pp1.ast.BoolInitializer;
import rs.ac.bg.etf.pp1.ast.BoolRef;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.CharInitializer;
import rs.ac.bg.etf.pp1.ast.CharRef;
import rs.ac.bg.etf.pp1.ast.CondFactTwoExpr;
import rs.ac.bg.etf.pp1.ast.ConstDecl;
import rs.ac.bg.etf.pp1.ast.ConstPart;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DotDesignator;
import rs.ac.bg.etf.pp1.ast.Equals;
import rs.ac.bg.etf.pp1.ast.For;
import rs.ac.bg.etf.pp1.ast.ForStatement;
import rs.ac.bg.etf.pp1.ast.FormParams;
import rs.ac.bg.etf.pp1.ast.FuncCallStatement;
import rs.ac.bg.etf.pp1.ast.FuncRef;
import rs.ac.bg.etf.pp1.ast.IntRef;
import rs.ac.bg.etf.pp1.ast.ListTerm;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodType;
import rs.ac.bg.etf.pp1.ast.MethodVoid;
import rs.ac.bg.etf.pp1.ast.MinusExpr;
import rs.ac.bg.etf.pp1.ast.MinusMinusStatement;
import rs.ac.bg.etf.pp1.ast.NotEquals;
import rs.ac.bg.etf.pp1.ast.NumCharBool;
import rs.ac.bg.etf.pp1.ast.NumberInitializer;
import rs.ac.bg.etf.pp1.ast.OperatorNewArray;
import rs.ac.bg.etf.pp1.ast.OperatorNewType;
import rs.ac.bg.etf.pp1.ast.ParenthesisExpr;
import rs.ac.bg.etf.pp1.ast.PlusPlusStatement;
import rs.ac.bg.etf.pp1.ast.PrintNoNumConstStatement;
import rs.ac.bg.etf.pp1.ast.PrintNumConstStatement;
import rs.ac.bg.etf.pp1.ast.ProcCallStatement;
import rs.ac.bg.etf.pp1.ast.ProcRef;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.ReturnExprStatement;
import rs.ac.bg.etf.pp1.ast.ReturnNoExprStatement;
import rs.ac.bg.etf.pp1.ast.ScalarParam;
import rs.ac.bg.etf.pp1.ast.SingleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleFactor;
import rs.ac.bg.etf.pp1.ast.SingleTerm;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarArrayPart;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarParticle;
import rs.ac.bg.etf.pp1.ast.VarRef;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	
	Obj currentMethod = null;

	private Struct varType = Tab.noType; //return type for group of variables
	private Struct constType = Tab.noType; //return type for group of constants
	private Struct methodType = Tab.noType;
	private Struct booleanStruct = new Struct(Struct.Bool);
	private boolean insideFor = false;
	private boolean isVoid = false;
	private boolean returnExists = false;
	private boolean mainExists = false;
	
	private boolean errorDetected = false;
	
	public int nVars;
	
	private int currentMethodNumberOfParams;
//	private List<ParamChecker> methodParamsChecker = new ArrayList<>();
//	private List<Struct> methodParamStruct;
//	private List<ParamChecker> methodCallParamChecke = new ArrayList<>();
//	private List<Struct> methodCallParamStruct;
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	/**
	 * Program visitors
	 */
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		if (!mainExists) {
			report_error("Nije nadjena funkcija main!", program);
		}
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}
	 
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	/*
	 * Variables visitors and help methods
	 */
	public void visit(VarDecl varDecl) {
		report_info("Povratna vrednost deklarisane promenljive je " + varDecl.getType().getType(), varDecl);
		varType = varDecl.getType().struct;
	}
	
	public void visit(VarParticle varParticle) {
		varDeclCount++;
		//check if user is not define any keyword or define the same variable twice
		if(this.checkVariablesCorrectivity(Tab.find(varParticle.getVarName()))) {
			report_info("Deklarisana promenljiva " + varParticle.getVarName(), varParticle);
			Obj varNode = Tab.insert(Obj.Var, varParticle.getVarName(), varType);
		}
	}
	
	public void visit(VarArrayPart arrayPart) {
		varDeclCount++;
		//check if user is not define any keyword or define the same variable twice
		if(this.checkVariablesCorrectivity(Tab.find(arrayPart.getVarName()))) {
			report_info("Deklarisan niz " + arrayPart.getVarName(), arrayPart);
			Tab.insert(Obj.Var, arrayPart.getVarName(), new Struct(Struct.Array, varType));
		}
	}
	
	private boolean checkVariablesCorrectivity(Obj var) {
		if (var != Tab.noObj) {
			if (var.getKind() == Obj.Prog || var.getKind() == Obj.Type) {
				report_error("Ne mozete koristiti naziv " + var.getName() + " za promenljivu, jer je to kljucna rec!", null);
				return false;
			}
			if (Tab.currentScope().findSymbol(var.getName()) != null) {
				report_error("Ne mozete deklarisati promenljiivu " + var.getName() + " jer je ona vec deklarisana u ovom opsegu!", null);
				return false;
			}
		}
		return true;
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getType());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip: " + type.getType() + " u tabeli simbola!", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				constType = varType = type.struct = typeNode.getType(); // adding to global variable in case of multiple decarations fe. int a, b, c;
			} else {
				report_error("Greska: Ime " + type.getType() + " ne predstavlja tip!", type);
				constType = varType = type.struct = Tab.noType;
			}
		}
	}

	public void visit(PrintNoNumConstStatement printNoNumConstStatement) {
		if (!simpleTypeOrArrayTypeComatible(printNoNumConstStatement.getExpr().struct)) {
			report_error("Greska na liniji " + printNoNumConstStatement.getLine() + " : Operand instrukcije print mora biti tipa char, int ili bool!", printNoNumConstStatement);
		}
		printCallCount++;
		log.info("Prepoznata naredba print");
	}
	
	public void visit(PrintNumConstStatement printNumConstStatement) {
		if (!simpleTypeOrArrayTypeComatible(printNumConstStatement.getExpr().struct)) {
			report_error("Greska na liniji " + printNumConstStatement.getLine() + " : Operand instrukcije print mora biti tipa char, int ili bool!", printNumConstStatement);
		}
		printCallCount++;
		log.info("Prepoznata naredba print");
	}
	
	private boolean simpleTypeOrArrayTypeComatible(Struct struct) {
			if (struct.getKind() == Struct.Array) 
				struct = struct.getElemType();
			return (struct == Tab.intType || struct == Tab.charType || struct == booleanStruct);
	}
	
	public void visit(ReadStatement statement) {
		if (!checkIfDesignatorVarFieldOrElem(statement.getDesignator().getDesignatorList().obj)) {
			report_error("Greska na liniji: " + statement.getLine() + " : Designator mora oznacavati promenljivu, element niza ili polje unutar objekta!", statement);
		}
		Struct type = statement.getDesignator().getDesignatorList().obj.getType();
		if (!(type == Tab.intType ||type == Tab.charType || type == booleanStruct)) {
			if ((type.getKind() == Struct.Array) && !(type.getElemType() == Tab.intType || type.getElemType() == Tab.charType))
					report_error("Greska na liniji " + statement.getLine() + " : Operand instrukcije read mora biti tipa char, int ili bool!", statement);
		}
	}
	
	/*
	 * Method visitors
	 */
	public void visit(MethodType methodType) {
		Struct methodT = methodType.getType().struct;
		isVoid = (methodT == Tab.noType);
		currentMethod = Tab.insert(Obj.Meth, methodType.getMethodName(), methodT);
		methodType.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodType.getMethodName(), methodType);
		
		currentMethodNumberOfParams = 0;
		//methodParamStruct = new ArrayList<>();
	}
	
	public void visit(MethodVoid methodVoid) {
		isVoid = true;
		currentMethod = Tab.insert(Obj.Meth, methodVoid.getMethodName(), Tab.noType); 
        methodVoid.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se void funkcija " + methodVoid.getMethodName(), methodVoid);
		
		currentMethodNumberOfParams = 0;
		//methodParamStruct = new ArrayList<>();
	}
	
	public void visit(MethodDecl decl) {
		//find main method here
		if (decl.getMethodReturnType().obj.getName().equals("main")) {
			report_info("Nadjena main metoda", decl);
			mainExists = true;
			//check that doesn't have parameters
			if (decl.getFormParameters() instanceof FormParams) {
				report_error("Main metoda ne sme imati parametre!", decl);
			}
			//check if return type is void
			if (decl.getMethodReturnType().obj.getType().getKind() != 0) {
				report_error("Main metoda mora biti tipa void!", decl);
			}
		} else if (!isVoid && !returnExists ) {
			report_error("Greska na liniji " + decl.getLine() + " : Metoda " + currentMethod.getName() +  " mora imati return iskaz, jer nije deklarisan sa void!", decl);
		}
		returnExists = false;
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		currentMethod = null;
	}
	
	public void visit(ReturnExprStatement returnExprStatement) {
		returnExists = true;
		Struct currentMethodType = currentMethod.getType();
		if (!currentMethodType.compatibleWith(returnExprStatement.getExpr().struct)) {
			if (currentMethodType.getKind() != Struct.Bool || returnExprStatement.getExpr().struct.getKind() != Struct.Bool)
				report_error("Greska na liniji " + returnExprStatement.getLine() + " : tip izraza u return naredbi se ne slaze sa tipom povratne vrednost metode " + currentMethod.getName(), returnExprStatement);
		}
	}
	
	// not necessary
	public void visit(ReturnNoExprStatement returnNoExprStatement) {
		if (!isVoid) {
			report_error("Greska na liniji " + returnNoExprStatement.getLine() + " : metoda mora imati return iskaz sa izrazom, jer nije deklarisan sa void!" , returnNoExprStatement);
		}
	}
	
	public void visit(ProcCallStatement procCallStatement) {
		if (procCallStatement.getDesignator().getDesignatorList().obj.getKind() != Obj.Meth) {
			report_error("Greska na liniji " + procCallStatement.getLine() + " : Designator mora biti metoda!", procCallStatement);
		} else {
			report_info("Pronadjen poziv metode " + procCallStatement.getDesignator().getDesignatorList().obj.getName() + " na liniji " + procCallStatement.getLine(), procCallStatement);
		}
	}
	
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	public void visit(FuncCallStatement funcCallStatement) {
		if (funcCallStatement.getDesignator().getDesignatorList().obj.getKind() != Obj.Meth) {
			report_error("Greska na liniji " + funcCallStatement.getLine() + " : Designator mora biti metoda!", funcCallStatement);
		}  else {
			report_info("Pronadjen poziv metode " + funcCallStatement.getDesignator().getDesignatorList().obj.getName() + " na liniji " + funcCallStatement.getLine(), funcCallStatement);
		}
	}
	
	public void visit(DotDesignator designator) {
		Obj obj = designator.getDesignatorList().obj;
		if (obj.getType().getKind() != Struct.Class) {
			report_error("Greska na liniji " + designator.getLine() + " : ime " + obj.getName() + " ne predstavlja klasu!", designator);
			designator.obj = Tab.noObj;
		} else {
			Obj ident = Tab.find(designator.getDesignator());
			if (ident == Tab.noObj || ident.getKind() != Obj.Fld || ident.getKind() != Obj.Meth) {
				report_error("Greska na liniji " + designator.getLine() + " : ime " + ident.getName() + " ne predstavlja polje klase ili metodu klase!", designator);
				designator.obj = Tab.noObj;
			} else {
				report_info("Pozvano polje ili metoda " + ident.getName() + " klase " +  obj.getName(), null);
				designator.obj = ident;
			}
		}
	}
	
	public void visit(FuncRef funcRef) {
		Obj func = funcRef.getDesignator().getDesignatorList().obj;
		if(Obj.Meth == func.getKind()) {
			if(Tab.noType == func.getType()) {
				funcRef.struct = Tab.noType;
				report_error("Greska! " + func.getName() + " se ne moze koristiti u izrazima jer nema povratnu vrednost!", funcRef);
			} else {
				report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcRef.getLine(), null);
				funcRef.struct = func.getType();
			}
		} else {
			report_error("Greska na liniji " + funcRef.getLine() + " : ime " + func.getName() + " nije funkcija", null);
			funcRef.struct = Tab.noType;	
		}
	}
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////	
	
	/***************************************************
	 ***************************************************
	 */
	public void visit(ArrayDesignator arrayDesignator) {
		Obj obj = arrayDesignator.getDesignatorList().obj;
		if (obj.getType().getKind() != Struct.Array) {
			report_error("Greska u liniji " + arrayDesignator.getLine() + " : ocekivan je niz!", arrayDesignator);
			arrayDesignator.obj = Tab.noObj;
		} else {
			if (arrayDesignator.getExpr().struct.getKind() != Struct.Int) {
				if (arrayDesignator.getExpr().struct.getKind() == Struct.Array) {
					report_info("Element niza deklarisan uz pomoc drugog elementa niza!", arrayDesignator);
					arrayDesignator.obj = obj;
				} else {
					report_error("Greska u liniji " + arrayDesignator.getLine() + " : ocekivan je int kao tip izmedju []!", arrayDesignator);
					arrayDesignator.obj = Tab.noObj;
				}
			} else {
				arrayDesignator.obj = obj;
			}
		}
	}
	
	public void visit(SingleDesignator singleDesignator) {
		Obj obj = Tab.find(singleDesignator.getDesignator());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + singleDesignator.getLine() + " : ime " + singleDesignator.getDesignator() + " nije deklarisano", singleDesignator);
		}
		singleDesignator.obj = obj;
	}
	
	public void visit(ProcRef procRef) {
		Obj proc = procRef.getDesignator().getDesignatorList().obj;
		if(Obj.Meth == proc.getKind()) {
			if(Tab.noType == proc.getType()) {
				procRef.struct = Tab.noType;
				report_error("Greska! " + proc.getName() + " se ne moze koristiti u izrazima jer nema povratnu vrednost!", procRef);
			} else {
				report_info("Pronadjen poziv procedure " + proc.getName() + " na liniji " + procRef.getLine(), procRef);
				procRef.struct = proc.getType();
			}
		} else {
			report_error("Greska na liniji " + procRef.getLine() + " : ime " + proc.getName() + " nije procedura", procRef);
			procRef.struct = Tab.noType;
		}
	}

	public void visit(ListTerm term) {
		Struct te = term.getTerm().struct;
		Struct fa = term.getFactor().struct;
		// this has to be implemented like this, because we want just integer values,
		// single or in array elem type in expresions for multiplication operations
		if (	(te.getKind() != Struct.Array && te == Tab.intType) && (fa.getKind() != Struct.Array && fa == Tab.intType) ||
				(te.getKind() == Struct.Array && fa == Tab.intType) ||
				(fa.getKind() == Struct.Array && te == Tab.intType) ||
				(te.getKind() == Struct.Array && fa.getKind() == Struct.Array && te.getElemType() == Tab.intType && fa.getElemType() == Tab.intType)
			) {
			term.struct = Tab.intType;
		} else {
			term.struct = Tab.noType;
			report_error("Greska na liniji " + term.getLine() + " : nekompatibilni tipovi u izrazu za mnozenje!", term);
		}
	}
	
	public void visit(AddOpExpr addOpExpr) {
		Struct te = addOpExpr.getExpr().struct;
		Struct fa = addOpExpr.getTerm().struct;
		// this has to be implemented like this, because we want just integer values,
		// single or in array elem type in expresions for addition operations
		if (	(te.getKind() != Struct.Array && te == Tab.intType) && (fa.getKind() != Struct.Array && fa == Tab.intType) ||
				(te.getKind() == Struct.Array && fa == Tab.intType) ||
				(fa.getKind() == Struct.Array && te == Tab.intType) ||
				(te.getKind() == Struct.Array && fa.getKind() == Struct.Array && te.getElemType() == Tab.intType && fa.getElemType() == Tab.intType)
			) {
			addOpExpr.struct = Tab.intType;
		} else {
			addOpExpr.struct = Tab.noType;
			report_error("Greska na liniji " + addOpExpr.getLine() + " : nekompatibilni tipovi u izrazu za sabiranje!", addOpExpr);
		}
	}
	
	public void visit(SingleFactor term) {
		term.struct = term.getFactor().struct;
	}
	
	public void visit(SingleTerm singleTerm) {
		singleTerm.struct = singleTerm.getTerm().struct;
	}
	
	public void visit(MinusExpr minusExpr) {
		if(minusExpr.getTerm().struct == Tab.intType) {
			minusExpr.struct = minusExpr.getTerm().struct;
		} else {
			minusExpr.struct = Tab.noType;
			report_error("Nekompatibilan tip, ocekivan int!", minusExpr);
		}
	}
	
	public void visit(IntRef intRef) {
		intRef.struct = Tab.intType;
	}
	
	public void visit(CharRef charRef) {
		charRef.struct = Tab.charType;
	}
	
	public void visit(BoolRef boolRef) {
		boolRef.struct = booleanStruct;
	}
	
	public void visit(VarRef ref) {
		ref.struct = ref.getDesignator().getDesignatorList().obj.getType();
	}
	
	public void visit(ParenthesisExpr expr) {
		expr.struct = expr.getExpr().struct;
	}
	
	/*
	 * Const visitors and methods
	 */
	public void visit(ConstDecl constDecl) {
		constType = constDecl.getType().struct;
	}
	
	public void visit(ConstPart constPart) {
		report_info("Deklarisana konstanta " + constPart.getConstName(), constPart);
		Obj constant = Tab.insert(Obj.Con, constPart.getConstName(), constType);
		if (!constType.assignableTo(constPart.getNumCharBool().struct)) {
			report_error("Greska na liniji " + constPart.getLine() + " : nekompatibilni tipovi u deklarisanju konstante!", null);
		}
		//set adr based on type of value
		if (constant != null) {
			NumCharBool numCharBool = constPart.getNumCharBool();
			if (numCharBool instanceof NumberInitializer)
				constant.setAdr(((NumberInitializer) numCharBool).getNumber());
			if (numCharBool instanceof CharInitializer)
				constant.setAdr(((CharInitializer) numCharBool).getCharacter());
			if (numCharBool instanceof BoolInitializer)
				constant.setAdr(((BoolInitializer) numCharBool).getBool() == "true" ? 1 : 0);
		}
	}
	
	public void visit(NumberInitializer initializer) {
		initializer.struct = Tab.intType;
	}
	
	public void visit(CharInitializer initializer) {
		initializer.struct = Tab.charType;
	}
	
	public void visit(BoolInitializer initializer) {
		initializer.struct = booleanStruct;
	}
	
	/*
	 * Designator statements
	 */
	public void visit(Assignment assignment) {
		Struct type = declareType(assignment.getDesignator().getDesignatorList().obj);
		
		if (!checkIfDesignatorVarFieldOrElem(assignment.getDesignator().getDesignatorList().obj)) {
			report_error("Greska na liniji: " + assignment.getLine() + " : Designator mora oznacavati promenljivu, element niza ili polje unutar objekta!", assignment);
		}
		
		if (!assignment.getExpr().struct.assignableTo(type)) {
			if (type.getKind() != Struct.Bool || assignment.getExpr().struct.getKind() != Struct.Bool)
				report_error("Greska na liniji " + assignment.getLine() + " : nekompatibilni tipovi u dodeli vrednosti!", assignment);
		}
	}
	
	public void visit(PlusPlusStatement statement) {
		Struct type = declareType(statement.getDesignator().getDesignatorList().obj);
		
		if (!checkIfDesignatorVarFieldOrElem(statement.getDesignator().getDesignatorList().obj)) {
			report_error("Greska na liniji: " + statement.getLine() + " : Designator mora oznacavati promenljivu, element niza ili polje unutar objekta!", statement);
		}
			
		if (type.getKind() != Struct.Int) {
			report_error("Greska na liniji " + statement.getLine() + " : " + statement.getDesignator().getDesignatorList().obj.getName() + " nije tipa int!", null);
		}
	}
	
	public void visit(MinusMinusStatement statement) {
		Struct type = declareType(statement.getDesignator().getDesignatorList().obj);
		
		if (!checkIfDesignatorVarFieldOrElem(statement.getDesignator().getDesignatorList().obj)) {
			report_error("Greska na liniji: " + statement.getLine() + " : Designator mora oznacavati promenljivu, element niza ili polje unutar objekta!", statement);
		}
		
		if (type.getKind() != Struct.Int) {
			report_error("Greska na liniji " + statement.getLine() + " : " + statement.getDesignator().getDesignatorList().obj.getName() + " nije tipa int!", null);
		}
	}
	
	private boolean checkIfDesignatorVarFieldOrElem(Obj obj) {
		if (obj.getKind() != Obj.Var || obj.getKind() != Obj.Fld || obj.getKind() != Obj.Elem) {
			return true;
		}
		return false;
	}
	
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	public void visit(For forTerminal) {
		insideFor = true;
	}
	
	public void visit(ForStatement forStatement) {
		insideFor = false;
	}
	
	public void visit(BreakStatement statement) {
		if (!insideFor) {
			report_error("Greska na liniji " + statement.getParent().getLine() + " : break nije unutar for petlje!", null);
		}
	}
	
	public void visit(ContinueStatement statement) {
		if (!insideFor) {
			report_error("Greska na liniji " + statement.getParent().getLine() + " : continue nije unutar for petlje!", null);
		}
	}
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	//////////////////////////////////////////////////////
	
	private Struct declareType(Obj obj) {
		Struct type = Tab.noType;
		if(obj.getType().getKind()==(Struct.Array)) {
			type = obj.getType().getElemType();
		} else {
			type = obj.getType();
		}
		return type;
	}
	
	public void visit(OperatorNewType operatorNewType) {
		Struct type = operatorNewType.getType().struct; 
		if (type.getKind() != Struct.Class) {
			report_error("Greska na liniji " + operatorNewType.getLine() + " : tip mora oznacavati klasu!", operatorNewType);
			operatorNewType.struct = Tab.noType;
		} else {
			report_info("Inicijalizovana nova klasa " + operatorNewType.getType() + " na liniji " + operatorNewType.getLine(), operatorNewType);
			operatorNewType.struct = type;
		}
	}
	
	public void visit(OperatorNewArray operatorNewArray) {
		Struct ex = operatorNewArray.getExpr().struct;
		if (ex.getKind() == Struct.Int) {
			report_info("New operator za niz!", operatorNewArray);
			operatorNewArray.struct = operatorNewArray.getType().struct;
		} else {
			report_error("Greska na liniji " + operatorNewArray.getLine() + " : izraz unutar [] mora biti tipa int!", operatorNewArray);
			operatorNewArray.struct = Tab.noType;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// NON CONSUMING PART OF THE CODE
	
	//+++++++++++++++++++++++++++++++++++++++++
		// new untested method
//		public void visit(FormParams formParams) {
//			methodParamsChecker.add(new ParamChecker(currentMethod.getName(), currentMethodNumberOfParams, methodParamStruct));
//		}
		
		public void visit(ScalarParam param) {
			Obj paramNode = Tab.insert(Obj.Var, param.getParamName(), param.getType().struct);
			
			paramNode.setFpPos(currentMethodNumberOfParams++);
			//methodParamStruct.add(paramNode.getType());
			
			report_info("Deklarisana promenjiva kao parametar : " + param.getParamName() + " tipa : " + param.getType().getType(), param);
		}
		
		public void visit(ArrayParam param) {
			Obj paramNode = Tab.insert(Obj.Var, param.getArrayName(), new Struct(Struct.Array, param.getType().struct));
			
			paramNode.setFpPos(currentMethodNumberOfParams++);
			//methodParamStruct.add(paramNode.getType());
			
			report_info("Deklarisan niz kao parametar : " + param.getArrayName() + " tipa : " + param.getType().getType(), param);
		}

	public void visit(CondFactTwoExpr condition) {
		Struct te1 = condition.getExpr().struct;
		Struct te2 = condition.getExpr1().struct;
		if ((te1.getKind() != Struct.Array && te1 == Tab.intType) && (te2.getKind() != Struct.Array && te2 == Tab.intType) ||
			(te1.getKind() == Struct.Array && te2 == Tab.intType) ||
			(te2.getKind() == Struct.Array && te1 == Tab.intType) ||
			(te1.getKind() == Struct.Array && te2.getKind() == Struct.Array && te1.getElemType() == Tab.intType && te2.getElemType() == Tab.intType)
		) {
			if (condition.getExpr().struct.getKind() == Struct.Array || condition.getExpr().struct.getKind() == Struct.Class) {
				if (!(condition.getRelop() instanceof Equals || condition.getRelop() instanceof NotEquals)) {
					report_error("Greska na liniji " + condition.getLine() + " : Za tip klase ili niza dozvoljeni relacioni operandi su iskljucivo == ili !=!", condition);
				}
			}
		} else {
			report_error("Greska na liniji " + condition.getLine() + " : Nekompatibilni izrazi u uslovnoj dodeli!", condition);
		}
	}
	
	public boolean passed() {
		return !errorDetected;
	}
 }
