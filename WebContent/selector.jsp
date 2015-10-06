
<div class="selector">
	<form action="Controler" method="GET">
		<input type="hidden" name="action" value="info"></input>
		<select name="country">
			<c:forEach items="${countries}" var="c">
				<option ${country != null && country.name eq c.name ? "selected" : ""} value="${c.name}">${c.name}</option>
			</c:forEach>
		</select>
		<button type="submit">Go!</button>
	</form>
</div>
