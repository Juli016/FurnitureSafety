package modelChecking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

class TransFur
{
	public TransVari []tv;
}

class TransNext
{
	String Mode;
	String condition;
	String next;
}

class TransVari
{
	String varName;
	ArrayList<TransNext> tnList = new ArrayList<TransNext>();
}

public class Convert2Smv {
	private static File fout = new File("F:\\fur.txt");
	private static Fsm fsm = new Fsm();
	
	public static void init()
	{
		fsm.init();
	}
	
	//打印前面初始化部分
	public static void outModule(PrintWriter output)
	{
		for(int i = 0; i < fsm.F_Array.length; i++)
		{
			output.printf("MODULE %s\r\n", fsm.F_Array[i].furname);
			output.println("VAR");
			//声明每个家具的状态变量
			if(fsm.F_Array[i].StateArr != null)
			{
				output.print("\tMode: {");
				output.print(fsm.F_Array[i].StateArr[0]);
				for(int j = 1; j < fsm.F_Array[i].StateArr.length; ++j)
				{
					output.print(",");
					output.print(fsm.F_Array[i].StateArr[j]);
				}
				output.print("};\r\n");
			}
			
			// 声明每个家具的内部变量
			if(fsm.F_Array[i].variArr != null)
			{
				for(int j = 0; j < fsm.F_Array[i].variArr.length; ++j)
				{
					output.printf("\t%s: ", fsm.F_Array[i].variArr[j].varName);
					switch(fsm.F_Array[i].variArr[j].varType)
					{
					case 0:
						output.print(fsm.F_Array[i].variArr[j].rage);
						break;
					case 1:
						output.printf("{%s}", fsm.F_Array[i].variArr[j].rage);
						break;
					case 2:
						output.print("boolean");
						break;
					default:
						System.out.println("Wrong variable type!");
					}
					output.print(";\r\n");
				}
			}
			
			output.println("ASSIGN");
			if(fsm.F_Array[i].initState != null)
				output.printf("\tinit(Mode) := %s;\r\n", fsm.F_Array[i].initState);
			if(fsm.F_Array[i].variArr != null)
			{
				for(int j = 0; j < fsm.F_Array[i].variArr.length; ++j)
				{
					if(fsm.F_Array[i].variArr[j].initVal != null)
						output.printf("\tinit(%s) := %s;\r\n",fsm.F_Array[i].variArr[j].varName , fsm.F_Array[i].variArr[j].initVal);
				}
			}
			
			output.print("\r\n");
		}
		System.out.println("Module initialized");
	}
	
	//在fsm中找到某个家具的某个action
	public static int findAction(int NO, String action)
	{
		assert(fsm.F_Array[NO].actionArr != null);
		for(int i = 0; i < fsm.F_Array[NO].actionArr.length; ++i)
		{
			if(fsm.F_Array[NO].actionArr[i].actionName == action)
			{
				return i;
			}
		}
		assert(false);
		return -1;
	}
	
	//在fsm中找到某个家具的某个属性变量
	public static int findVariable(int NO, String variable)
	{
		assert(fsm.F_Array[NO].variArr != null);
		for(int i = 0; i < fsm.F_Array[NO].variArr.length; ++i)
		{
			if(fsm.F_Array[NO].variArr[i].varName == variable)
			{
				return i;
			}
		}
		assert(false);
		return -1;
	}
	
	//得到某个家具的编号
	public static String getNO(int i)
	{
		String s;
		s = "_" + "" + i + ".";
		return s;
	}
	
	//输出后面的 module main
	public static void outMain(PrintWriter output)
	{
		output.println("MODULE main");
		output.println("VAR");
		for(int i = 0; i < fsm.F_Array.length; i++)
		{
			output.printf("\t_%d: %s;\r\n", i, fsm.F_Array[i].furname);
		}
		System.out.println("Main initialized");
		output.println("ASSIGN");
		
		TransFur[] tf = new TransFur[fsm.F_Array.length];
		for(int i = 0; i < fsm.F_Array.length; ++i)
		{
			tf[i] = new TransFur();
			
			int numOfVari = 1;		
			if(fsm.F_Array[i].variArr != null)
				numOfVari += fsm.F_Array[i].variArr.length;
			tf[i].tv = new TransVari[numOfVari];
			for(int j = 0; j < numOfVari; ++j)
				tf[i].tv[j] = new TransVari();
			
			tf[i].tv[0].varName = "Mode";
			if(fsm.F_Array[i].variArr != null)
			{
				for(int j = 1; j < numOfVari; ++j)
				{
					tf[i].tv[j].varName = fsm.F_Array[i].variArr[j-1].varName;
				}
			}
		}
		
		for(int i = 0; i < fsm.F_Array.length; ++i)
		{
			// 在内部Rules中改变的变量
			if(fsm.F_Array[i].internRules != null)
			{
				for(int j = 0; j < fsm.F_Array[i].internRules.length; ++j)
				{				
					String action = fsm.F_Array[i].internRules[j].dstAction;
					int n = findAction(i, action);
					
					String transMode = null;
					//对起始状态有要求
					if(fsm.F_Array[i].actionArr[n].startState != null)
					{
						transMode = getNO(i) + "Mode=" + fsm.F_Array[i].actionArr[n].startState[0];
						for(int q = 1; q < fsm.F_Array[i].actionArr[n].startState.length; ++q)
						{
							transMode += "|" + getNO(i) + "Mode=" + fsm.F_Array[i].actionArr[n].startState[q];
						}
					}
					
					String Condition = null;
					Condition = getNO(i);
					Condition += fsm.F_Array[i].internRules[j].srcVar;
					Condition += fsm.F_Array[i].internRules[j].condition;
					
					// 状态的切换
					TransNext tmp = new TransNext();
					tmp.Mode = transMode;
					tmp.condition = Condition;
					tmp.next = fsm.F_Array[i].actionArr[n].endState;
					tf[i].tv[0].tnList.add(tmp);
					
					if(fsm.F_Array[i].actionArr[n].trans != null)
					{
						for(int t = 0; t < fsm.F_Array[i].actionArr[n].trans.length; ++t)
						{
							// 变量值的改变
							String variable = fsm.F_Array[i].actionArr[n].trans[t].val;
							// 根据变量名找到相应变量
							int m = findVariable(i, variable);
							TransNext tmp1 = new TransNext();
							tmp1.Mode = transMode;
							tmp1.condition = Condition;
							tmp1.next = fsm.F_Array[i].actionArr[n].trans[t].valRst;
							tf[i].tv[m+1].tnList.add(tmp1);
						}
					}
				}
			}
		}
		
		// 用户设定的的rules
		if(fsm.commonRules != null)
		{
			for(int i = 0; i < fsm.commonRules.length; ++i)
			{
				int srcNO = fsm.commonRules[i].srcFur;
				int dstNO = fsm.commonRules[i].dstFur;
				String action = fsm.commonRules[i].dstAction;
				int n = findAction(dstNO, action);
				
				String transMode = null;
				//对起始状态有要求
				if(fsm.F_Array[dstNO].actionArr[n].startState != null)
				{
					transMode = getNO(dstNO) + "Mode=" + fsm.F_Array[dstNO].actionArr[n].startState[0];
					for(int q = 1; q < fsm.F_Array[dstNO].actionArr[n].startState.length; ++q)
					{
						transMode += "|" + getNO(dstNO) + "Mode=" + fsm.F_Array[dstNO].actionArr[n].startState[q];
					}
				}
				
				String Condition = null;
				Condition = getNO(srcNO);
				Condition += fsm.commonRules[i].srcVar;
				Condition += fsm.commonRules[i].condition;
				
				// 状态的切换
				TransNext tmp = new TransNext();
				tmp.Mode = transMode;
				tmp.condition = Condition;
				tmp.next = fsm.F_Array[dstNO].actionArr[n].endState;
				tf[dstNO].tv[0].tnList.add(tmp);
				
				if(fsm.F_Array[dstNO].actionArr[n].trans != null)
				{
					for(int t = 0; t < fsm.F_Array[dstNO].actionArr[n].trans.length; ++t)
					{
						// 变量值的改变
						String variable = fsm.F_Array[dstNO].actionArr[n].trans[t].val;
						// 根据变量名找到相应变量
						int m = findVariable(dstNO, variable);
						TransNext tmp1 = new TransNext();
						tmp1.Mode = transMode;
						tmp1.condition = Condition;
						tmp1.next = fsm.F_Array[dstNO].actionArr[n].trans[t].valRst;
						tf[dstNO].tv[m+1].tnList.add(tmp1);
					}
				}
			}
		}
		
		//输出main中的assign
		for(int i = 0; i < tf.length; ++i)
		{
			for(int j = 0; j < tf[i].tv.length; ++j)
			{
				if(tf[i].tv[j].tnList.size() != 0)
				{
					output.printf("\tnext(%s):=\r\n",getNO(i)+tf[i].tv[j].varName);
					output.print("\tcase\r\n");
					for(int k = 0; k < tf[i].tv[j].tnList.size(); ++k)
					{
						output.print("\t\t");
						boolean hasCond = false, hasMode = false;
						if(tf[i].tv[j].tnList.get(k).condition != null)
							hasCond = true;
						if(tf[i].tv[j].tnList.get(k).Mode != null)
							hasMode = true;
						if(hasCond)
							output.printf("(%s)",tf[i].tv[j].tnList.get(k).condition);
						if(hasCond && hasMode)
							output.print("&");
						if(hasMode)
							output.printf("(%s)",tf[i].tv[j].tnList.get(k).Mode);
						output.printf(":%s;\r\n",tf[i].tv[j].tnList.get(k).next);
					}
					output.print("\t\tTRUE:");
					output.print(getNO(i)+tf[i].tv[j].varName);
					output.print(";\r\n");
					
	/*				if(j == 0)
					{
						output.print("{");
						output.print(fsm.F_Array[i].StateArr[0]);
						for(int k = 1; k < fsm.F_Array[i].StateArr.length; ++k)
						{
							output.print(",");
							output.print(fsm.F_Array[i].StateArr[k]);
						}
						output.print("};\r\n");
					}
					else
					{
						switch(fsm.F_Array[i].variArr[j-1].varType)
						{
						case 0:
							output.print(fsm.F_Array[i].variArr[j-1].rage);
							break;
						case 1:
							output.printf("{%s}", fsm.F_Array[i].variArr[j-1].rage);
							break;
						case 2:
							output.print("{TRUE,FALSE}");
							break;
						default:
							System.out.println("Wrong variable type!");
						}
						output.print(";\r\n");
					}*/
					output.printf("\tesac;\r\n\r\n");
					
				}
			}
		}
	}
	
	public static void main(String []args) {
		init();
		PrintWriter output;
		try {
			output = new PrintWriter(fout);
			outModule(output);
			outMain(output);
			output.print("CTLSPEC AG(_2.Mode = Alarm -> AX(_1.Mode = Refresh));\r\n");
			output.print("CTLSPEC AG(_2.Mode = Alarm -> AX(_3.Mode = Alarm));\r\n");
			output.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
