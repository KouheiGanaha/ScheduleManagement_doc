処理が複雑そうな実装箇所

	//イベント登録結果画面
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html"
	xmlns:th="http://www.tyemeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>イベント登録結果画面</title>
</head>
<body>
	<form th:object="${eventRegistForm}">
	<H2>イベント登録 結果</H2>
	//イベントURLの表示
    <p>イベントURL<input type="text" name="eventUrl" th:value="${eventUrl}"/></p>
    
    //出欠回答画面への遷移ボタン
	<a href="/event/answerRegist" th:href="@{/event/answerRegist(Url=${map.get('EVENT_URL')})}">出欠回答</a>
	</form>
</body>
</html>



	//出欠回答画面
	
	//Contorller
	/**
	 * 初期表示画面
	 * @param model
	 * @return 出欠回答画面
	 */
	@RequestMapping("/event/answerRegist")
	public String answerRegistIndex(@RequestParam("Url") String Url, Model model) {

		//イベント情報取得
		Map<String, Object> eventData = answerRegistService.getEventData(Url);
		model.addAttribute("eventData",eventData);
		
		//イベントの候補日取得
		List<Map<String,Object>> eventDate = answerRegistService.getEventDate(eventData);
		model.addAttribute("eventDate",eventDate);
		
		//回答の選択肢取得
		Map<String,String> getSelectItems = answerRegistService.getSelectItems();
		model.addAttribute("selectItems",getSelectItems);

		model.addAttribute("answerRegistForm", new AnswerRegistForm());
		return "event/answerRegist";
	}
	
	
	//Service
	
	//イベント情報取得
	@Override
	public Map<String,Object> getEventData(String eventUrl){
	Map<String,Object> eventData = answerRegistAccessor.getsEventData(eventUrl);
	return eventData;
	}

	//イベントの候補日取得
	@Override
	public List<Map<String,Object>> getEventDate(Map<String,Object> eventData){
		String eventId = eventData.get("EVENT_ID").toString();
		List<Map<String,Object>> eventDate = answerRegistAccessor.getEventDate(eventId);
		return eventDate;
	}
	
	//回答の選択肢取得
	public Map<String,String> getSelectItems(){
		Map<String,String> selectMap = new LinkedHashMap<String,String>();
		selectMap.put("key_A","○");
		selectMap.put("key_B","△");
		selectMap.put("key_C","×");
		return selectMap;
	}
	
	
	
	