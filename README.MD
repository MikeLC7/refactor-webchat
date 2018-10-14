---------------------
##### 2018/10/14 GitHubInitVersion

---------------------


---------------------
##### 2018/06/26 InitVersion

---------------------
1、使用Jfinal作为基础；封装为SpringCloud版本；
2、将Jfinal配置类上提至Rest模块，
    - 通过@Configuration实现项目启动时加载Jfinal相关配置文件，实现普通RestfulAPI对Jfinal相关调用；
    - 通过在配置类(WebConfig)里添加FilterRegistrationBean实现指定过滤，指定“/webchatOrg/”相对路径的链接为访问原生态（Jfinal）;
    - 并且“/webchatOrg/”属于微信自发的请求，跳过匹配原有的OrgParameter等参数相关的过滤器；

3、以上，实现项目本身RestfulAPI和Jfinal原生方法的并存；

>
InitVersion：
仅实现链接的分享功能和用户授权；

---------------------
