import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
/*
 * 구간 반복 프로그램
 * input 파일에 반복하고 싶은 채보를 넣고 저장
 * 뛰어넘고 싶은 구간 길이를 jump에 입력
 * 프로그램 돌리고 output파일에 출력됨
 * 그 내용을 복사 붙여넣기 OK??
 * 파일 이름 바꿔도 됨
 * 파일 경로 바꿔도 됨
 */

 

public class practice06 {

	public static void main(String[] args) {
		try {
			InputStream fis = new FileInputStream("C:\\Users\\input.txt");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			FileOutputStream fos = new FileOutputStream("C:\\Users\\\output.txt");
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			
			String data = br.readLine();
			String output = " ";
			String[] data_split = data.split(" ");		
			int beat_num = data_split.length;
			String key;
			double beat_time;
			
			//여기 jump 값 수정하셈
			double jump = 0;
				
			
			
			for(int j=0; j < beat_num; j++) {
				key = data_split[j].substring(0,1);
				beat_time = Double.parseDouble(data_split[j].substring(1));	
				beat_time += jump;
				output += key;
				output += Double.toString(beat_time);
				output += " ";
			}
			bw.write(output);
			
			br.close();isr.close();fis.close();
			bw.flush();
			bw.close();fos.close();osw.close();
		} catch(IOException e) {e.printStackTrace();}

	}

}
