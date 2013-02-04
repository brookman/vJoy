#ifdef GL_ES
precision highp float;
#endif

uniform float time;
uniform vec2 resolution;

void main(){
	vec3 col;
	float l, z = time;
	for(int i = 0; i < 3; i++){
		vec2 uv,r = resolution, p = gl_FragCoord.xy / r;
		uv =  p;
		p -= 0.5;
		p.x *= r.x / r.y;
		z += 0.07, l = length(p), uv += p / l * (sin(z) + 1.0) * abs(sin(l * 9.0 - z * 2.0)), col[i] = 0.01 / length(abs(mod(uv, 1.0) - 0.5));
	}
	gl_FragColor=vec4(col/l,time);
}