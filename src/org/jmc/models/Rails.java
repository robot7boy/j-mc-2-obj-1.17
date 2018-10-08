package org.jmc.models;

import java.util.HashMap;

import org.jmc.geom.Transform;
import org.jmc.geom.Vertex;
import org.jmc.threading.ChunkProcessor;
import org.jmc.threading.ThreadChunkDeligate;


/**
 * Model for minecart rails.
 */
public class Rails extends BlockModel
{

	@Override
	public void addModel(ChunkProcessor obj, ThreadChunkDeligate chunks, int x, int y, int z, HashMap<String, String> data, int biome)
	{
		Transform rotate = new Transform();
		Transform translate = new Transform();
		Transform rt;
		
		boolean ascending = false;
		boolean curved = false;
		boolean powered = Boolean.parseBoolean(data.get("powered"));
		
		switch (data.get("shape"))
		{
			case "north_south":
				break;
			case "east_west":
				rotate.rotate(0, 90, 0);
				break;
			case "ascending_north":
				ascending = true;
				break;
			case "ascending_east":
				ascending = true;
				rotate.rotate(0, 90, 0);
				break;
			case "ascending_south":
				ascending = true;
				rotate.rotate(0, 180, 0);
				break;
			case "ascending_west":
				ascending = true;
				rotate.rotate(0, -90, 0);
				break;
			case "south_east":
				curved = true;
				break;
			case "south_west":
				curved = true;
				rotate.rotate(0, 90, 0);
				break;
			case "north_west":
				curved = true;
				rotate.rotate(0, 180, 0);
				break;
			case "north_east":
				curved = true;
				rotate.rotate(0, -90, 0);
				break;
		}
		translate.translate(x, y, z);		
			
		rt = translate.multiply(rotate);
		
		String mtl;
		if (curved || powered) {
			mtl = materials.get(data,biome)[1];
		}
		else
		{
			mtl = materials.get(data,biome)[0];
		}
		
		Vertex[] vertices = new Vertex[4];
		if (!ascending)
		{
			// flat
			vertices[0] = new Vertex(-0.5f, -0.47f,  0.5f);
			vertices[1] = new Vertex( 0.5f, -0.47f,  0.5f);
			vertices[2] = new Vertex( 0.5f, -0.47f, -0.5f);			
			vertices[3] = new Vertex(-0.5f, -0.47f, -0.5f);
			obj.addFace(vertices, null, rt, mtl);
		}
		else
		{
			// ascending
			vertices[0] = new Vertex(-0.5f, -0.47f,  0.5f); 				
			vertices[1] = new Vertex( 0.5f, -0.47f,  0.5f);	
			vertices[2] = new Vertex( 0.5f,  0.53f, -0.5f);				
			vertices[3] = new Vertex(-0.5f,  0.53f, -0.5f);
			obj.addFace(vertices, null, rt, mtl);
		}
	}

}
