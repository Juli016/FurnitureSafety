package modelChecking;

public class Fsm
{
	public Furniture[] F_Array;
	public Rule[] commonRules;
	
	// 假设我收到的json被组织成这样的格式，方便我后期建模
	public void init()
	{
		commonRules = new Rule[6];
		for(int i = 0; i < 6; ++i)
			commonRules[i] = new Rule();
	
		commonRules[0].srcFur = 6;
		commonRules[0].srcVar = "Mode";
		commonRules[0].condition = "=Open";
		commonRules[0].dstFur = 5;
		commonRules[0].dstAction = "OpenLight";

		commonRules[1].srcFur = 4;
		commonRules[1].srcVar = "temperature";
		commonRules[1].condition = ">35";
		commonRules[1].dstFur = 1;
		commonRules[1].dstAction = "blowColdBreeze";
		
		commonRules[2].srcFur = 4;
		commonRules[2].srcVar = "temperature";
		commonRules[2].condition = "<10";
		commonRules[2].dstFur = 1;
		commonRules[2].dstAction = "blowWarmBreeze";
		
		commonRules[3].srcFur = 2;
		commonRules[3].srcVar = "Mode";
		commonRules[3].condition = "=Alarm";
		commonRules[3].dstFur = 1;
		commonRules[3].dstAction = "refreshAir";
		
		commonRules[4].srcFur = 2;
		commonRules[4].srcVar = "Mode";
		commonRules[4].condition = "=Alarm";
		commonRules[4].dstFur = 3;
		commonRules[4].dstAction = "startAlarm";
		
		commonRules[5].srcFur = 6;
		commonRules[5].srcVar = "Mode";
		commonRules[5].condition = "=Open";
		commonRules[5].dstFur = 0;
		commonRules[5].dstAction = "SwitchToVolumn1";
		
		F_Array = new Furniture[7];
		for(int i = 0; i < 7; i++)
			F_Array[i]=new Furniture();
		
		F_Array[0].furname = "TV";
		F_Array[0].StateArr = new String[]{"Open", "Closed"};
		F_Array[0].initState = "Closed";
		
		F_Array[0].variArr = new Variable[1];
		F_Array[0].variArr[0] = new Variable();
		F_Array[0].variArr[0].varName = "volumn";
		F_Array[0].variArr[0].varType = 0;
		F_Array[0].variArr[0].rage = "1..10";
		
		F_Array[0].actionArr = new Action[3];
		for(int i = 0; i < 3; i++)
			F_Array[0].actionArr[i]=new Action();
		F_Array[0].actionArr[0].actionName = "closeTV";
		F_Array[0].actionArr[0].startState = new String[]{"Open"};
		F_Array[0].actionArr[0].endState = "Closed";
		
		F_Array[0].actionArr[1].actionName = "openTV";
		F_Array[0].actionArr[1].startState = new String[]{"Closed"};
		F_Array[0].actionArr[1].endState = "Open";
		
		F_Array[0].actionArr[2].actionName = "SwitchToVolumn1";
		F_Array[0].actionArr[1].startState = new String[]{"Open","Closed"};
		F_Array[0].actionArr[2].endState = "Open";
		F_Array[0].actionArr[2].trans = new TransVar[1];
		F_Array[0].actionArr[2].trans[0] = new TransVar();
		F_Array[0].actionArr[2].trans[0].val = "volumn";
		F_Array[0].actionArr[2].trans[0].valRst = "1";
		
		
		F_Array[1].furname = "AirConditioner";
		F_Array[1].StateArr = new String[]{"WarmWind", "ColdWind", "Refresh", "Closed"};
		F_Array[1].initState = "Closed";
		
		F_Array[1].variArr = new Variable[1];
		F_Array[1].variArr[0] = new Variable();
		F_Array[1].variArr[0].varName = "speed";
		F_Array[1].variArr[0].varType = 1;
		F_Array[1].variArr[0].rage = "high, medium, low";
		
		F_Array[1].actionArr = new Action[4];
		for(int i = 0; i < 4; ++i)
			F_Array[1].actionArr[i] = new Action();
		F_Array[1].actionArr[0].actionName = "blowWarmBreeze";
		F_Array[1].actionArr[0].startState = new String[]{"Closed", "Refresh", "ColdWind"};
		F_Array[1].actionArr[0].endState = "WarmWind";
		
		F_Array[1].actionArr[1].actionName = "blowColdBreeze";
		F_Array[1].actionArr[1].startState = new String[]{"WarmWind", "Closed", "Refresh"};
		F_Array[1].actionArr[1].endState = "ColdWind";
		
		F_Array[1].actionArr[2].actionName = "refreshAir";
		F_Array[1].actionArr[2].startState = new String[]{"WarmWind", "Closed", "ColdWind"};
		F_Array[1].actionArr[2].endState = "Refresh";
		
		F_Array[1].actionArr[3].actionName = "CloseAirConditioner";
		F_Array[1].actionArr[3].startState = new String[]{"WarmWind", "ColdWind", "Refresh"};
		F_Array[1].actionArr[3].endState = "Closed";
		
		
		F_Array[2].furname = "SmokeDetector";
		F_Array[2].StateArr = new String[]{"Alarm", "Normal"};
		F_Array[2].initState = "Normal";
		
		F_Array[2].variArr = new Variable[1];
		F_Array[2].variArr[0] = new Variable();
		F_Array[2].variArr[0].varName = "smokeDensity";
		F_Array[2].variArr[0].varType = 0;
		F_Array[2].variArr[0].rage = "0..50";
		F_Array[2].variArr[0].initVal = "0";
		
		F_Array[2].actionArr = new Action[2];
		for(int i = 0; i < 2; ++i)
			F_Array[2].actionArr[i] = new Action();
		F_Array[2].actionArr[0].actionName = "startAlarm";
		F_Array[2].actionArr[0].startState = new String[]{"Normal"};
		F_Array[2].actionArr[0].endState = "Alarm";
		
		F_Array[2].actionArr[1].actionName = "endAlarm";
		F_Array[2].actionArr[1].startState = new String[]{"Alarm"};
		F_Array[2].actionArr[1].endState = "Normal";
		
		F_Array[2].internRules = new Rule[2];
		for(int i = 0; i < 2; ++i)
			F_Array[2].internRules[i] = new Rule();
		
		F_Array[2].internRules[0].srcVar = "smokeDensity";
		F_Array[2].internRules[0].condition = ">20";
		F_Array[2].internRules[0].dstAction = "startAlarm";
		
		F_Array[2].internRules[1].srcVar = "smokeDensity";
		F_Array[2].internRules[1].condition = "<=20";
		F_Array[2].internRules[1].dstAction = "endAlarm";
		
		
		F_Array[3].furname = "AlarmClock";
		F_Array[3].StateArr = new String[]{"Alarm", "Quiet"};
		F_Array[3].initState = "Quiet";
		
		F_Array[3].variArr = new Variable[1];
		F_Array[3].variArr[0] = new Variable();
		F_Array[3].variArr[0].varName = "Time";
		F_Array[3].variArr[0].varType = 0;
		F_Array[3].variArr[0].rage = "0..23";
		
		F_Array[3].actionArr = new Action[2];
		for(int i = 0; i < 2; ++i)
			F_Array[3].actionArr[i] = new Action();
		F_Array[3].actionArr[0].actionName = "startAlarm";
		F_Array[3].actionArr[0].startState = new String[]{"Quiet"};
		F_Array[3].actionArr[0].endState = "Alarm";
		
		F_Array[3].actionArr[1].actionName = "endAlarm";
		F_Array[3].actionArr[1].startState = new String[]{"Alarm"};
		F_Array[3].actionArr[1].endState = "Quiet";
		
		
		F_Array[4].furname = "Thermometer";
		F_Array[4].StateArr = new String[]{"On","Off"};
		F_Array[4].initState = "On";
		
		F_Array[4].variArr = new Variable[1];
		F_Array[4].variArr[0] = new Variable();
		F_Array[4].variArr[0].varName = "temperature";
		F_Array[4].variArr[0].varType = 0;
		F_Array[4].variArr[0].rage = "-5..40";
		

		F_Array[5].furname = "Light";
		F_Array[5].StateArr = new String[]{"On", "Off"};
		F_Array[5].initState = "Off";
		
		F_Array[5].variArr = new Variable[1];
		F_Array[5].variArr[0] = new Variable();
		F_Array[5].variArr[0].varName = "Degree";
		F_Array[5].variArr[0].varType = 1;
		F_Array[5].variArr[0].rage = "high, medium, low";
		
		F_Array[5].actionArr = new Action[2];
		for(int i = 0; i < 2; ++i)
			F_Array[5].actionArr[i] = new Action();
		F_Array[5].actionArr[0].actionName = "OpenLight";
		F_Array[5].actionArr[0].startState = new String[]{"Off"};
		F_Array[5].actionArr[0].endState = "On";
		
		F_Array[5].actionArr[1].actionName = "closeLight";
		F_Array[5].actionArr[1].startState = new String[]{"On"};
		F_Array[5].actionArr[1].endState = "Off";
		
		
		F_Array[6].furname = "Door";
		F_Array[6].StateArr = new String[]{"Open", "Closed"};
		
		F_Array[6].actionArr = new Action[2];
		for(int i = 0; i < 2; ++i)
			F_Array[6].actionArr[i] = new Action();
		F_Array[6].actionArr[0].actionName = "openDoor";
		F_Array[6].actionArr[0].startState = new String[]{"Closed"};
		F_Array[6].actionArr[0].endState = "Open";
		
		F_Array[6].actionArr[1].actionName = "closeDoor";
		F_Array[6].actionArr[1].startState = new String[]{"Open"};
		F_Array[6].actionArr[1].endState = "Closed";
		
		System.out.println("Furnitures initialized");
	}
}
