#!use/bin/env python
#coding:utf-8

import os, urllib, urllib2, MySQLdb, time,  platform

def log_w(text):
    logfile = 'tmp/websocket.log'
    if os.path.isfile(logfile):
        if (os.path.getsize(logfile)/1024/1024) > 100:
            os.remove(logfile)
    now = time.strftime('%Y-%m-%d %H:%M:%S')
    tt = str(now) + '\t' + str(text) + '\n'
    f = open(logfile, 'a+')
    f.write()
    f.close()

def get_idcname(ip):
    try:
        conn = MySQLdb.connect(
            host = '127.0.0.1',
            port = 3306,
            user = 'root',
            passwd = '123456',
            db = 'test',
            )
        cursor = conn.cursor()
        sql = "select host,user from mysql.user where host='%s'" %ip
        cursor.execute(sql)
        alldata = cursor.fetchall()
        print alldata
        cursor.close()
        conn.close()
        return alldata[0][0].encode('UTF8')
    except Exception ,e:
        print e
        return 0

def get_ip():
    os = platform.system()
    if os == 'Linux':
        ip = os.popen("/sbin/ifconfig eth0|grep 'inet addr'").read().strip().splot(":")[1].split
    elif os == 'Windows':
        import wmi
        c = wmi.WMI()
        network = c.Win32_NetworkAdapterConfiguration(IPEnabled=1)
        for interface in network:
            if interface.DefaultIPGateway:
                ip = interface.IPAddress[0]
    return ip


def web_status():
    ip = get_ip()
    idc_name = get_idcname(ip)
    url = 'http://www.text.com/index.php?idc_ip=%s&idc_name=%s' %(ip, idc_name)
    get = urllib2.urlopen(url)
    print idc_name
    return
    if get.getcode() == 200:
        ##print get.read().strip()
        aa = int(get.read().strip())
        if aa == 1:
            text = 'webservice return ok'
        else:
            text = 'webservice return error'
    else:
        text = 'conect webservice error'

    print text
    log_w(text)


if __name__ == '__main__':
    web_status()


#add by 201905060959
#add by hzj 201905061015
