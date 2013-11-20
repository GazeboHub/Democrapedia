package ws.gazebo.brontes.runner.test;

public class TestApp {

	public static void main(String[] args) {
		System.out.println("Hello World");
		System.err.println("Args were: ");
		for(String a : args){
			System.out.println('\t' + a);
		}

	}

}
