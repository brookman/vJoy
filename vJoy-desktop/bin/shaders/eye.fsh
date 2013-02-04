#ifdef GL_ES
precision highp float;
#endif

uniform float time;
uniform vec2 resolution;

float rand(vec2 co) {
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

float hash(float n) {
	return fract(sin(n) + 43758.5453);
}

float noise(vec3 x) {
	vec3 p = floor(x);
	vec3 k = fract(x);
	k = k * k * (3.0 - 2.0 * k);
	
	float n = p.x + p.y * 57.0 + p.z * 113.0;
	float a = hash(n);
	float b = hash(n + 1.0);
	float c = hash(n + 57.0);
	float d = hash(n + 58.0);
	float e = hash(n + 113.0);
	float f = hash(n + 114.0);
	float g = hash(n + 170.0);
	float h = hash(n + 171.0);
	float res = mix(mix(mix(a, b, k.x), mix(c, d, k.x), k.y),
					mix(mix(e, f, k.x), mix(g, h, k.x), k.y),
					k.z);
	return res;
}

float noise(vec2 x) {
	return noise(vec3(x, sin(x.y)));
}

mat2 m = mat2(0.8, 0.6, -0.6, 0.8);

float fbm(vec2 p) {
	float f = 0.0;
	f += 0.5 * noise(p); p *= m*2.02;
	f += 0.25 * noise(p); p *= m*2.03;
	f += 0.125 * noise(p); p *= m*2.01;
	f += 0.0625 * noise(p);
	f /= 0.9375;
	return f;
}

void main(void) {
	vec2 q = gl_FragCoord.xy / resolution.xy;
	vec2 p = -1.0 + 2.0 * q;
	p.x *= resolution.x / resolution.y;
	
	float r = sqrt(dot(p,p));
	float a = atan(p.y, p.x);
	
	vec3 background = vec3(0.2, 0.2, 0.2);
	float g = fbm(2.0 * p)*0.4;
	background = mix(background, vec3(g), fbm(1.0 * p));
	vec3 col = background;
	
	float ss = 0.5 + 0.5 * sin(time * 0.7);
	ss += 0.5 + 0.5 * sin(time * 0.845 + 0.234);
	float anim = 1.0 + 0.3 * ss * clamp(0.6 - r, 0.0, 1.0);
	r *= anim;
	
	if(r < 0.8) {
		col = vec3(1.0, 0.0, 0.0);
		
		float f = fbm(5.0 * p);
		col = mix(col, vec3(0.4, 0.2, 0.0), f);
		
		f = 1.0 - smoothstep(0.2, 0.5, r);
		col = mix(col, vec3(0.2, 0.4, 0.1), f);
		
		a += 0.03 * fbm(30.0 * p);
		
		f = smoothstep(0.3, 1.0, fbm(vec2(6.0 * r, 20.0 * a)));
		col = mix(col, vec3(0.3), f);
		
		f = smoothstep(0.4, 0.9, fbm(vec2(10.0 * r, 15.0 * a)));
		col *= 1.0 - 0.7 * f;
		
		f = smoothstep(0.65, 0.8, r);
		col *= 1.0 - 0.8 * f;
		
		f = smoothstep(0.2, 0.3, r);
		col *= f;
		
		f = 1.0 - smoothstep(0.0, 0.5, length(p - vec2(0.24, 0.2)));
		col += vec3(1.0, 0.6, 0.5) * f * 0.9;
		
		f = smoothstep(0.75, 0.8, r);
		col = mix(col, background, f);
	}
	
    gl_FragColor = vec4(col, 1.0);
}