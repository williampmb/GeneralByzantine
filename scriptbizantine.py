#!/usr/bin/python

"""
This example shows how to create an empty Mininet object
(without a topology object) and add nodes to it manually.
"""

from mininet.net import Mininet
from mininet.node import Controller
from mininet.cli import CLI
from mininet.log import setLogLevel, info
from multiprocessing import Process
from time import sleep
import argparse

def version():
    return "v2.5"

def startXterm(node):
    node.popen()

def startBroadCastServer(node):
    info('*** Starting Broadcast Server ***')
    node.cmdPrint( 'ifconfig')
    node.cmdPrint( 'java -jar /home/williampmb/Desktop/Client-Server/Servidor-v2.0.jar')
    

def startGeneral(node, numberGenerals, probrability):
    info('*** Starting General ***')
    serverIp = '10.0.0.1'
    node.cmdPrint( 'java -jar /home/williampmb/Desktop/Client-Server/ByzantineFailureModelClient-v3.3.jar %s %s %s' %(serverIp,numberGenerals,probrability))

def emptyNet(loyal, traitor):

    "Create an empty network and add nodes to it."
    server = 1
    numbGenerals = int(loyal) + int(traitor)
    loyalLeft = int(loyal)
    traitorLeft = int(traitor)

    net = Mininet( controller=Controller )

    info( '*** Adding controller\n' )
    net.addController( 'c0' )

    info( '*** Adding hosts\n' )
    HostsList = []
    for i in range( 0, (numbGenerals+server)):
    	host = net.addHost( 'h%s'%(i+1), ip='10.0.0.%s'%(i+1))
    	HostsList.append(host)
    	

    info( '*** Adding switch\n' )
    s1 = net.addSwitch( 's1' )

    info( '*** Creating links\n' )
    for i in range( 0, (numbGenerals+server)):
    	net.addLink( HostsList[i], s1 )


    p = Process(target=startBroadCastServer, args=(HostsList[0],))
    
    sleep(1)

    processList = []
    for i in range( 1, (numbGenerals+server)):
        if loyalLeft > 0:
            c = Process(target=startGeneral, args=(HostsList[i],numbGenerals, -1))
            loyalLeft-=1
        else:
            c = Process(target=startGeneral, args=(HostsList[i],numbGenerals, 999))
            traitorLeft-=1
        processList.append(c)
        sleep(0.1)
    
    if (loyalLeft == 0 and traitorLeft ==0 ):
        print("** ALL SET  Loyals Left: %i Traitors Left: %i **" %(loyalLeft,traitorLeft))
    else:
        print("** SOMEHTING WRONG WITH NUMBER OF TRAITORS AND NUMBER OF LOYALS  Left: %i Traitors Left: %i **" %(loyalLeft,traitorLeft)) 

    info( '*** Starting network\n')
    net.start()

    p.start()
    sleep(2)
    for generals in processList:
        generals.start()

    info( '*** Running CLI\n' )
    CLI( net )

    info( '*** Stopping network' )
    net.stop()


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("loyal", help="Number of Loyal Generals")
    parser.add_argument("traitor", help="Number of Traitor Generals")
    args = parser.parse_args()

    setLogLevel( 'info' )
    emptyNet(args.loyal, args.traitor)
