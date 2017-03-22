#coding: utf-8


# http://www.iyirer.com/details/4836.html
baseURL = 'http://www.iyirer.com'
mm_num = 4807
total = 150

import re, urllib, urllib2, os
import time
from time import ctime, sleep


##代理IP
user_agent ='"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36"'
headers = { 'User-Agent' : user_agent }  

class Spider:
    def __init__(self):
        self.url = baseURL + '/details/' + str(mm_num)

    def getPage(self):
        for i in range(1, total):
            my_url = self.url + '_' +str(i) + '.html'
            print my_url

            maxTryNum=5
            for tries in range(maxTryNum):  
                try:  
                    request = urllib2.Request(my_url, headers = headers)
                    Response = urllib2.urlopen(request)
                    content = Response.read()
                    Response.close()
                    break  
                except:  
                    if tries <(maxTryNum-1):  
                        continue  
                    else:  
                        logging.error("Has tried %d times to access url %s, all failed!",maxTryNum,url)  
                        break   
            ##request = urllib2.Request(my_url, headers = headers)
            ##content = urllib2.urlopen(request).read()

            ##print content
            
            pattern1 = re.compile('<a href=/details/'+ str(mm_num) + '_\d+.html><img src="(.*?)"', re.S)
            imgUrl = re.findall(pattern1, content)
            ##print imgUrl

            ##print 2
            
            pattern2 = re.compile('<a href=/details/'+ str(mm_num) + '_(\d+).html><img src="', re.S)
            name = re.findall(pattern2, content)
            filename1 = int(name[0]) - 1
            filename = str(filename1)
            ##print filename
            
            real_imgUrl = baseURL + imgUrl[0]
            print real_imgUrl
            self.save_image(real_imgUrl, filename)

            
            time.sleep(5)
            print '%s done!\n' %ctime()
            
        
    def save_image(self, my_imgUrl, filename):
        self.mkdir(str(mm_num))

        maxTryNum=3
        for tries in range(maxTryNum):  
            try:  
                u = urllib.urlopen(my_imgUrl)   
                break  
            except:  
                if tries <(maxTryNum-1):  
                    continue  
                else:  
                    ##logging.error("Has tried %d times to access url %s, all failed!",maxTryNum,url)  
                    break
        #u = urllib.urlopen(my_imgUrl)
        data = u.read()
        u.close()
        real_fileName = str(mm_num) + '/' + filename + '.jpg'
        f = open(real_fileName, 'wb')
        f.write(data)
        f.close()
        
    def mkdir(self,path):
        path = path.strip()
        
        isExists=os.path.exists(path)
  
        if not isExists:
            print 'New a path named ', path
            os.makedirs(path)
            return True
        else:
            return False

def test_url():
    i_url = 'http://www.iyirer.com/details/4836.html'
    request = urllib2.Request(i_url, headers = headers)
    Response = urllib2.urlopen(request)
    content = Response.read()
    Response.close()
    print content
    

if __name__ == '__main__':
    spider = Spider()
    spider.getPage()
