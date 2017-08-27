package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	
	HashSet<Capitalist> allCapitalists = new HashSet<Capitalist>();

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * 
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * 
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy 
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    
    public boolean add(Capitalist capitalist) {
    	
    	// A HashSet will not allow duplicates, so even though this method will send duplicate parents to...
    	// ... the collection, the HashSet will simply bounce them into garbage collection.
        if (capitalist == null || !capitalist.hasParent() && capitalist instanceof WageSlave) {
        	return false;
        }
        else {
        	add(capitalist.getParent());
        }
        return allCapitalists.add(capitalist);
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
    	
        if (allCapitalists.contains(capitalist))
        	return true;
        else
        	return false;
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */

	@Override
    public Set<Capitalist> getElements() {
		
    	Set<Capitalist> newSet = new HashSet<>(allCapitalists);
    	return newSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	
        Set<FatCat> demCats = new HashSet<>();
        
        for (Capitalist capitalist: allCapitalists) {
        	if (capitalist instanceof FatCat)
        		demCats.add((FatCat) capitalist);
        }
        return demCats;
    }
        

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	
        Set<Capitalist> kiddieSet = new HashSet<>();
        
    	if (!has(fatCat))
    		return kiddieSet;
    	
        for (Capitalist kiddo: allCapitalists) {
        	if (fatCat == kiddo.getParent())
        		kiddieSet.add(kiddo);
        }
        return kiddieSet;
    }
    

    /** 
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
        
    	Map<FatCat, Set<Capitalist>> catMap =  new HashMap<>();

        for (FatCat fatCat: getParents()) {
        	catMap.put(fatCat, getChildren(fatCat)); 
        }
        return catMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	
        List<FatCat> parentChain = new ArrayList<>();
        
        if (!has(capitalist))
        	return parentChain;
        
    	if (capitalist.hasParent()) {
        	FatCat nextUp = capitalist.getParent();
        	while (nextUp != null) {
        		parentChain.add(nextUp);
        		nextUp = nextUp.getParent();
        	}
    	}
        return parentChain;
    }

  
    // Over-ridden hashCode and equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allCapitalists == null) ? 0 : allCapitalists.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MegaCorp other = (MegaCorp) obj;
		if (allCapitalists == null) {
			if (other.allCapitalists != null)
				return false;
		} else if (!allCapitalists.equals(other.allCapitalists))
			return false;
		return true;
	}
    
    
    

}
