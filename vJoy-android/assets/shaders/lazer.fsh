#ifdef GL_ES
   #define LOWP lowp
   precision high float;
#else
   #define LOWP
#endif

uniform float uSpeed1;
uniform float uSpeed2;
uniform float uSpeed3;
uniform float time;
uniform vec2 resolution;

varying vec2 vTextureCoord;

void main(void) {
	gl_FragColor = vec4(0.0, 0.0, 0.0, 0.03);
	
	for(float shift = 0.0; shift < 0.2; shift += 0.005) {
		vec2 p = vec2(0.0, 0.0);
		float rt = time + shift;
		p.x += cos(rt * uSpeed1 * 20.0) * 0.1;
		p.y += sin(rt * uSpeed1 * 20.0) * 0.1;
	
		p.x += cos(rt * uSpeed2 * 40.0) * 0.07;
		p.y += sin(rt * uSpeed2 * 40.0) * 0.07;
	
		p.x += cos(rt * uSpeed3 * 60.0) * 0.03;
		p.y += sin(rt * uSpeed3 * 60.0) * 0.03;
		
		p += vec2(0.5, 0.5);
		
		if(distance(p, vTextureCoord) < 0.004) {
			gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
			//break;
		}
	}
}