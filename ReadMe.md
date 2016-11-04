# Introduction

## Related links to develop
1. Nginx:
   * Install: http://jingyan.baidu.com/article/1e5468f97f3275484961b7df.html
   * Setting: http://blog.csdn.net/wang379275614/article/details/47778201
   * Some updates, Maybe it will also be lost the z-lib library.
2. Shiro:
   * General Introduction: http://jinnianshilongnian.iteye.com/blog/2018936
   * Using redis to share session: http://jinnianshilongnian.iteye.com/blog/2018936
   * Ehcache: http://www.cnblogs.com/whc321/p/5543068.html
   * Important parts:
     * Realm(Two "do" method)
     * Session Factory(Create method to identify if it's on redis)
     * Session Dao
     * SimpleSession override(Mark if it should be update to redis)
3. Redis
   * Spring-data-redis: http://projects.spring.io/spring-data-redis/
   * SDR Transaction: http://www.cnblogs.com/qijiang/p/5626461.html
   * Redis subscribe and publish: http://www.open-open.com/lib/view/open1351324403395.html


## Deploy and run steps
1. Use maven plugin(package demand) or just use export war method to export the war package.
2. Copy the .war file to all the tomcat webapp folder.
3. Use terminal to run the redis in the /opt/redis/src folder redis-server file. Run under default port on default.
4. Run all the tomcat under/opt/, use/apache-tomcat/bin, sh startup.sh.
5. Run the ngnix under/usr/local/nginx/sbin/. Use ./nginx to run.
6. Browser http://localhost/ShiroTest to browse it. Since the Ngnix is running under 80 port by default.
   </br>So if the 80 port is used by other server such as Apache, it will goes wrong. Nginx can be used as the http server only.

***
Last update : 11/04/2016 by poisson0106
</br>copyright @ poisson0106(poisson0106@gmail.com)