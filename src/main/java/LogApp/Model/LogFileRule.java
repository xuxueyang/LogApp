package LogApp.Model;

/**
 * 仅仅用于写文件的规范
 */
public class LogFileRule {
    private LogFileRule(){

    }
    //Meta
    {
//        year:
//        [
//                month:[
//                   week:[
//                           day:[
//                                   each]]     ]
//                ]
//        所以开始要读取配置文件——1.样式全局配置文件（包含数据文件路径）2.备忘录文件路径（记录最后一次操作的缓存文件）  3.数据文件META，4.数据data
//        然后根据这些文件初始化
//        或者简单点，直接把日志数据树写成文件路径的形式也是阔以的，这样就不用自己手写路径树（这点阔以参照那些印象笔记这些文件的写法）
//        最好对于每个Model写一下meta和data文件，这样的话，emmm将LogEach也抽象成“文件夹”会有利于后续的统一与操作，meta里记录id和子id（和父id）以及
    }
    //LeEach
    {
//        {
//            "isOver": false,
//                "isDiary": true,
//                "isExist": true,
//                "uuid": "957a943481fc4f03aa4fd7c585c91447",
//                "createDate": {
//            "dateTime": {
//                "date": {
//                    "year": 2017,
//                            "month": 12,
//                            "day": 8
//                },
//                "time": {
//                    "hour": 11,
//                            "minute": 34,
//                            "second": 15,
//                            "nano": 694000000
//                }
//            },
//            "offset": {
//                "totalSeconds": 28800
//            },
//            "zone": {
//                "id": "Asia/Shanghai"
//            }
//        },
//            "updateData": {
//            "dateTime": {
//                "date": {
//                    "year": 2017,
//                            "month": 12,
//                            "day": 8
//                },
//                "time": {
//                    "hour": 11,
//                            "minute": 34,
//                            "second": 15,
//                            "nano": 694000000
//                }
//            },
//            "offset": {
//                "totalSeconds": 28800
//            },
//            "zone": {
//                "id": "Asia/Shanghai"
//            }
//        },
//            "filePath": "",
//                "classify": "Default"
//        }
    }
}
