package INFO6205_517;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import INFO6205_517.GenerationTool2.BI;

public class InvolveLine implements Callable<List<BI>> {
	
	private boolean[] line;
	
	public void setLine(boolean[] line) {
		this.line = line;
	}
	
	public List<BI> call() throws IOException, InterruptedException{
		GenerationTool2 gt3 = new GenerationTool2();
		List<BI> tmp = gt3.run(line);
		Thread.sleep(1000);
		return tmp;
	}
}
