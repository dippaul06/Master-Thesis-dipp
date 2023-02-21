

"""
with open('/home/dipp/Github/Playground/Data/tags.json') as f:
    lines = [line for line in f]
  
print(lines)
  
# removing the new line characters
"""

with open('/home/dipp/Github/Playground/Data/tags.json') as f:
    lines = [line.rstrip() for line in f]
  
print(lines)