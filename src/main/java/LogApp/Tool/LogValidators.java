package LogApp.Tool;

public  class LogValidators {
    public static <T> boolean isInRange(T str,T ... values){
        if(isEmpty(str))
            return false;
        if("".equals(str))
            return false;
        for(T tmp:values){
            if(str.equals(tmp)){
                return true;
            }
        }
        return false;
    }
    public static <T> boolean isEmpty(T object){
        if(object==null)
            return true;
        return false;
    }
}
