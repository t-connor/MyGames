##coding: utf-8
import os, re, urllib2
import time

f = open('123.txt', 'a+')
for i in range(0, 111):
    url = 'http://www.*******'+ str(i) +'/'
    request = urllib2.Request(url)
    response = urllib2.urlopen(request)
    content = response.read()
    response.close()
 
     ##print content
    compare = r'[^>][&nbsp;]*(.*?)<br'
    text_content = re.findall(compare, content)

    for text in text_content:
        f.write( '\t' + text + '\n\n')
    print str(i) + 'is done!'
    time.sleep(5)

f.close()
