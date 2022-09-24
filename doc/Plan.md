# Problem 1

## Phase 0: Requirements

It's gotta print the binary tree sideways.  with indentation for each level of the nodes

**What info will the recursive call need?**
* the depth of the call
* the node to be added

## Phase 1: System Analysis

**What algorithms?**



## Phase 2: Design

It will need to do an `in-order traversal` and with each
`node` it finds the depth and appends `depth * " " + node` that to the return string

```
  14
 11
10
   9
  8
   7
 6
  5
```

pseudocode
```java

private String toString(Node<E> t, String indent) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append toString(t.right, indent + " ") + "\n"
        sb.append indent + t.element.toString() + "\n"
        sb.append toString(t.right, indent + " ") + "\n"
        return sb.toString();
    
    
        }


```



# Problem 2

## Phase 0: Requirements

flip the children

![img.png](img.png)

## Phase 1: System Analysis

## Phase 2: Design

```java
public void flip(node<E> t){
        if (t == null) return
        flip the children of t
        flip(t.left)
        flip(t.right)
        }


```


# Problem 3

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design
```java

private NodeInfo deepestNode(BinaryNode<E> node, int depth){
        NodeInfo leftBiggest;
        NodeInfo rightBigeest;
        if (node.left){
            leftBiggest =  = deepestNode(node.left, depth+1)
        }
        if (node.right){
            rightBiggeset =  = deepestNode(node.left, depth+1)
        }
        if (leftBiggest && rightBiggest){
            return biggest of the two
        }
        else if(leftBiggest || rightBiggest){
            return the one that continues
        }
        else
            return the root node, // no children
    }
```

# Problem 4

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 5

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 6

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 7

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 8

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 9

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design


# Problem 10

## Phase 0: Requirements

## Phase 1: System Analysis

## Phase 2: Design
