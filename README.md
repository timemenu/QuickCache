# QuickCache<br/>
android image cache library<br/>
(안드로이드 이미지캐시 모듈입니다.)
<br/>

___
인터페이스 QuickCache<br/>
* putBitmap(key(String), bitmap(Bitmap))
* putBitmap(key(String), file(File))
* getBitmap(key(String))
* clear()

___
#### 1. 캐시 생성<br/>
 1. 메모리캐시<br/>
QuickCacheGenerator.getInstance().createMemoryCache(cacheName, cacheSize);<br/>
 2. 디스크캐시<br/>
QuickCacheGenerator.getInstance().createDiskCache(context, cacheName, cacheSize);<br/>
 3. 체인캐시(컨텍스트, 캐시이름, 캐시사이즈, 포맷, 품질)<br/>
QuickCacheGenerator.getInstance().createCainCache(context, cacheName, cacheSize, format, quality);

___

#### 2. 캐시 호출<br/>
QuickCache quickCache = QuickCacheGenerator.getInstance().getCache(cacheName);<br/>
Bitmap bitmap = quickCache.getBitmap(key);<br/>
if (bitmap != null) {<br/>
<br/>
}<br/>

___

#### 3. 캐시 추가<br/>
quickCache.putBitmap(key, someBitmap);<br/>
