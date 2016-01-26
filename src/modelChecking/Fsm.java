package modelChecking;

public class Fsm
{
	public Furniture[] F_Array;
	public Rule[] commonRules;
	
	// �������յ���json����֯�������ĸ�ʽ�������Һ��ڽ�ģ
	public void test()
	{
		F_Array = new Furniture[7];
		F_Array[0].furname = "TV";
		F_Array[0].StateArr = new String[]{"Open", "Closed"};
		F_Array[0].initState = "Closed";
		
		F_Array[0].variArr = new Variable[1];
		F_Array[0].variArr[0].varName = "volumn";
		F_Array[0].variArr[0].varType = 0;
		F_Array[0].variArr[0].rage = "0..10";
		
		F_Array[0].actionArr = new Action[3];
		F_Array[0].actionArr[0].actionName = "closeTV";
		F_Array[0].actionArr[0].startState = new String[]{"Open"};
		F_Array[0].actionArr[0].endState = "Closed";
		
		F_Array[0].actionArr[1].actionName = "openTV";
		F_Array[0].actionArr[1].startState = new String[]{"Closed"};
		F_Array[0].actionArr[1].endState = "Open";
		
		F_Array[0].actionArr[2].actionName = "SwitchToVolumn1";
		F_Array[0].actionArr[2].trans = new TransVar[1];
		F_Array[0].actionArr[2].trans[0].val = "volumn";
		F_Array[0].actionArr[2].trans[0].valRst = "1";
		
		
		F_Array[1].furname = "AirConditioner";
		F_Array[1].StateArr = new String[]{"WarmWind", "ColdWind", "Refresh", "Closed"};
		F_Array[1].initState = "Closed";
		
		F_Array[1].variArr = new Variable[1];
		F_Array[1].variArr[0].varName = "speed";
		F_Array[1].variArr[0].varType = 1;
		F_Array[1].variArr[0].rage = "high, medium, low";
		
		F_Array[1].actionArr = new Action[4];
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
		F_Array[2].variArr[0].varName = "smokeDensity";
		F_Array[2].variArr[0].varType = 0;
		F_Array[2].variArr[0].rage = "0..50";
		F_Array[2].variArr[0].initVal = "0";
		
		F_Array[2].actionArr = new Action[2];
		F_Array[2].actionArr[0].actionName = "startAlarm";
		F_Array[2].actionArr[0].startState = new String[]{"Normal"};
		F_Array[2].actionArr[0].endState = "Alarm";
		
		F_Array[2].actionArr[1].actionName = "endAlarm";
		F_Array[2].actionArr[1].startState = new String[]{"Alarm"};
		F_Array[2].actionArr[1].endState = "Normal";
		
		F_Array[2].internRules = new Rule[2];
		F_Array[2].internRules[0].srcFur = 2;
		F_Array[2].internRules[0].srcVar = "smokeDensity";
		F_Array[2].internRules[0].condition = ">20";
		F_Array[2].internRules[0].dstFur = 2;
		F_Array[2].internRules[0].dstAction = "startAlarm";
		
		F_Array[2].internRules[1].srcFur = 2;
		F_Array[2].internRules[1].srcVar = "smokeDensity";
		F_Array[2].internRules[1].condition = "<=20";
		F_Array[2].internRules[1].dstFur = 2;
		F_Array[2].internRules[1].dstAction = "endAlarm";
		
		
		F_Array[3].furname = "AlarmClock";
		F_Array[3].StateArr = new String[]{"Alarm", "Quiet"};
		F_Array[3].initState = "Quiet";
		
		F_Array[3].variArr = new Variable[1];
		F_Array[3].variArr[0].varName = "Time";
		F_Array[3].variArr[0].varType = 0;
		F_Array[3].variArr[0].rage = "0..23";
		
		F_Array[3].actionArr = new Action[2];
		F_Array[3].actionArr[0].actionName = "startAlarm";
		F_Array[3].actionArr[0].startState = new String[]{"Quiet"};
		F_Array[3].actionArr[0].endState = "Alarm";
		
		F_Array[3].actionArr[1].actionName = "endAlarm";
		F_Array[3].actionArr[1].startState = new String[]{"Alarm"};
		F_Array[3].actionArr[1].endState = "Quiet";
		
		
		F_Array[4].furname = "Thermometer";
		F_Array[4].StateArr = new String[]{"Open"};
		F_Array[4].initState = "Open";
		
		F_Array[4].variArr = new Variable[1];
		F_Array[4].variArr[0].varName = "temperature";
		F_Array[4].variArr[0].varType = 0;
		F_Array[4].variArr[0].rage = "-5..40";
		

		F_Array[5].furname = "Light";
		F_Array[5].StateArr = new String[]{"On", "Off"};
		F_Array[5].initState = "Off";
		
		F_Array[5].variArr = new Variable[1];
		F_Array[5].variArr[0].varName = "Degree";
		F_Array[5].variArr[0].varType = 1;
		F_Array[5].variArr[0].rage = "high, medium, low";
		
		F_Array[5].actionArr = new Action[2];
		F_Array[5].actionArr[0].actionName = "OpenLight";
		F_Array[5].actionArr[0].startState = new String[]{"Off"};
		F_Array[5].actionArr[0].endState = "On";
		
		F_Array[5].actionArr[1].actionName = "closeLight";
		F_Array[5].actionArr[1].startState = new String[]{"On"};
		F_Array[5].actionArr[1].endState = "Off";
		
		
		F_Array[6].furname = "Door";
		F_Array[6].StateArr = new String[]{"Open", "Closed"};
		
		F_Array[6].actionArr = new Action[2];
		F_Array[6].actionArr[0].actionName = "openDoor";
		F_Array[6].actionArr[0].startState = new String[]{"Closed"};
		F_Array[6].actionArr[0].endState = "Open";
		
		F_Array[6].actionArr[1].actionName = "closeDoor";
		F_Array[6].actionArr[1].startState = new String[]{"Open"};
		F_Array[6].actionArr[1].endState = "Closed";
	}
}