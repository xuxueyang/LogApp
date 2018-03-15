package LogApp.Controller;

import LogApp.Tool.LogEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by –Ï—©—Ù on 2017/12/6.
 */
public interface LogCtrInterface {
      List<String> resolveRange = new ArrayList<String>();
      LogEvent execute(LogEvent logEvent);
}
