package hospital

class PageFilters {

    def navService

    def filters = {
        all(controller:'*', action:'*') {
            after = { Map model ->
                if (model != null) {
                    model.mainNav = navService.get()
                    model.pageNav = navService.getCurrentPageNav(request)
                }
            }
        }
    }
}
