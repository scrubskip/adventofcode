#!/usr/bin/env python
# 

from parse import compile

def main():
    file = open("/Users/kip/src/adventofcode/day3/day3input.txt", "r")
    fabric = [[None]*1000 for _ in range(1000)]
    candidates = set()
    for claim_str in file:
        claim = Claim(claim_str)
        claim_id = claim.data['claim_id']
        print "adding ", claim.data
        candidates.add(claim_id)
        #print claim.data
        for x in range(claim.data['x'], claim.data['x'] + claim.data['width']):
            for y in range(claim.data['y'], claim.data['y'] + claim.data['height']):
                if (fabric[x][y] is None):
                    fabric[x][y] = set()
                fabric[x][y].add(claim_id)
                # print "[{0:d},{1:d}]: {2} ".format(x, y, fabric[x][y])
                if (len(fabric[x][y]) > 1):
                    for remove_claim_id in fabric[x][y]:
                        if (remove_claim_id in candidates):
                            print "removing ", remove_claim_id
                            candidates.remove(remove_claim_id)
        print "current size ", len(candidates)
    
    for key in candidates:
        print key

class Claim:
    # sample: #1 @ 257,829: 10x23
    parser = compile("#{claim_id:d} @ {x:d},{y:d}: {width:d}x{height:d}")

    def __init__(self, claim_str):
        self.data = self.parser.parse(claim_str, True)


if __name__== "__main__":
    main()