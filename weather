# coding: utf-8
import re
import code
import urllib2

city = raw_input ('which city you want to know:\n')

url = ('http://flash.weather.com.cn/wmaps/xml/'+ str(city) +'.xml')
content = urllib2.urlopen(url).read()

f = file('weather.xml', 'w')
f.write(content)
f.close()


print content

try:

    compare_num = r'\d+'
    comoare_weather = r'stateDetailed="(.+?)"'
    compare_cityname = 'cityname="(.+?)"'

    num = re.findall(compare_num, content)
    weather = re.findall(comoare_weather, content)
    cityname = re.findall(compare_cityname, content)
except:
    print 'input error'
##print isinstance(weather[0], unicode)


print '天气播报：'
print cityname[0].decode('utf-8').encode('gb2312') 
print weather[0].decode('utf-8').encode('gb2312') 
print '%s-%s ℃' % (num[-7], num[-9])   ##酱紫不行啊~~
print '现在室外温度' + num[-6] + '℃'
print u'监测时间' + '%s:%s' % (num[-3], num[-2])


