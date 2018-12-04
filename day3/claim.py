#!/usr/bin/env python
# 

from parse import compile

def main():
    file = open("day3input.txt", "r")
    fabric = [[0]*1000 for _ in range(1000)]
    for claim_str in file:
        claim = Claim(claim_str)
        #print claim.data
        claim_fabric(fabric, claim)
        
    contested = 0
    for x in range(1000):
        for y in range(1000):
            if fabric[x][y] > 1:
                contested += 1
    
    return contested

def claim_fabric(fabric, claim):
    for x in range(claim.data['x'], claim.data['x'] + claim.data['width']):
        for y in range(claim.data['y'], claim.data['y'] + claim.data['height']):
            fabric[x][y] += 1

class Claim:
    # sample: #1 @ 257,829: 10x23
    parser = compile("#{claim_id:d} @ {x:d},{y:d}: {width:d}x{height:d}")

    def __init__(self, claim_str):
        self.data = self.parser.parse(claim_str, True)


if __name__== "__main__":
  print main()