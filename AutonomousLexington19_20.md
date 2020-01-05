# Autonomous Planning

The autonomous program will act in phases, indicated by the `phase_counter` variable.  Each phase is a single action taken by the robot.  Simple actions include `drive forward` or `rotate cw 90 deg`.  After a condition has been met, usually after a certain time period, or sensor value, the `phase_counter` can then be incremented to the next phase.  

## Goals

- 10pts Repositioning the Building site 
- 5pts  parking on the line

- 10pts for the first or second SkyStone
- 2pts  delivering piece to build zone 
- 2pts  piece on foundation

Note: Red/Blue teams directions should be mirrored.  
Original Instructions for Red Team.

## Simplest Autonomous - Parking

Starting position: In stone zone

- Drive forward to line and park (variable distance)

Starting position: In stone zone

- Drive Sideways to the right or left (variable distance)
- Drive forward to line and park (variable distance)

## Medium Difficulty Autonomous - Foundation Move

Recommended: 
`public double foundation_distance()` function implementation

Starting Position: in Building Site

- Lift Arm a little bit (Can be a built in arm for this)
- Drive forward to foundation (Direction can be different)
- Drop Arm all the way 
- Drive Backwards


## Harder Difficulty Autonomous - Stone Manipulation

Requires: 
`public boolean stone_detected()` function implementation

## Even harder difficulty autonomous - Move Foundation and Park

Recommended: 
`public double foundation_distance()` function implementation
