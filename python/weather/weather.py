# coding: utf-8
import re
import code
import urllib2

def check_weather():

    try:
        compare_temp1 = r'tem1="([-]?\d+)"'
        compare_temp2 = r'tem2="([-]?\d+)"'
        compare_tempNow = r'temNow="([-]?\d+)"'
        comoare_weather = r'stateDetailed="(.+?)"'
        compare_cityname = 'centername="(.+?)"'
        compare_windState = 'windState="(.+?)"'

        temp1 = re.findall(compare_temp1, content)
        temp2 = re.findall(compare_temp2, content)
        tempNow = re.findall(compare_tempNow, content)
        weather = re.findall(comoare_weather, content)
        windState = re.findall(compare_windState, content)
        cityname = re.findall(compare_cityname, content)
        
        ##print temp1
        ##print temp2
            
    except:
        print 'input error'
        
    if len(cityname) == 0:
        print 'Can not find the city.'
        return

    print u'\n天气播报：\n'
            
    for i in range(len(cityname)):
        print cityname[i].decode('utf-8').encode('gb2312'),
        print '\t',
        print weather[i].decode('utf-8').encode('gb2312'),
        print '\t',

        if int(temp1[i]) >= int(temp2[i]): ##sometimes temp1<temp2
            print u'%s-%s ℃\t' % (temp2[i], temp1[i]),
        else:
            print u'%s-%s ℃\t' % (temp1[i], temp2[i]),
        try:
            print u'现在室外温度' + tempNow[i] + u'℃\t',
        except:
            print
            continue
        ##print windState[i].decode('utf-8').encode('gb2312')
        print windState[i].decode('utf-8')
    print
        

if __name__ == '__main__':
    
    city = raw_input ('which city you want to know(ues PINYIN):\n')
    newCity = city.lower()

    while newCity != 'exit' and newCity != 'quit' and newCity != 'close':
        url = ('http://flash.weather.com.cn/wmaps/xml/'+ str(newCity) +'.xml')
        try:
            content = urllib2.urlopen(url).read()
        except:
            print 'Input error!'
            break

        ##print content

        check_weather()
        print 
        city = raw_input ('which city you want to know(ues PINYIN):\n')
        newCity = city.lower()
        
