package modelChecking;

class Rule {
	int srcFur;				// if语句后家具的编号
	String srcVar;			// 第一个家具某个属性名称
	String condition;		// 第一个家具满足的条件
	
	int dstFur;				// then语句后的家具编号
	String dstAction;		// 第二个家具执行的action名称
}

class Spec {
	String spec;
}

class Variable
{
	String varName;		// 变量名称
	int varType;		// 变量类型，(0为int型，1为enum型，2为boolean型)
	String rage;		// 变量范围

	String initVal;		// 初始化值，若没有初始化值为空字符串
	String curVal;		// 当前取值
}

class TransVar			// 在action中改变variable的值
{
	String val;			// 要改变的家具变量在variArr中的名称
	String valRst;		// 改变后的值
}

class Action
{
	String actionName;			// action名称 
	String[] startState;		// 起始的State
	String endState;			// 结束的State
	TransVar[] trans;			// 执行的transitions
}

class Furniture
{
	String furname;					// 家具名称
	String curState;				// 家具当前状态编号
	String initState;				// 家具初始状态，如没有为空字符串

	String[] StateArr;				// 所有的状态列表
	Variable[] variArr;				// 所有的内部变量
	Action[] actionArr;				// 可以执行的action列表
	Rule[] internRules;				// 内部规则
}

public class Common {
	public native boolean Check(Rule[] R_Array, Spec[] S_Array, Furniture[] F_Array);
	private static Rule[] R_Array;
	private static Spec[] S_Array;
	private static Furniture[] F_Array;

	static {
		System.loadLibrary("Check");
	}

	public static void main(String[] args) {
		new Common().Check(R_Array, S_Array, F_Array);
	}	
}
