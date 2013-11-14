package wtp;

public class Converter {
	
	public String suma(int x, int y) {
		/*String result = "<x>"+x+"</x>"
				+ "<y>"+y+"</y>";*/
		int result = x + y;
		String s_result=String.valueOf(result);
		return "result:"+s_result;
	}
	
}
