#
# @lc app=leetcode id=1518 lang=python3
#
# [1518] Water Bottles
#

# @lc code=start
class Solution:
    def numWaterBottles(self, numBottles: int, numExchange: int) -> int:
        emptyBottles = 0
        drink = 0
        while numBottles > 0:
            drink += numBottles
            exchangedBottles = int((numBottles + emptyBottles) / numExchange)
            emptyBottles = numBottles + emptyBottles - exchangedBottles * numExchange
            numBottles = exchangedBottles
        return drink
        
# @lc code=end