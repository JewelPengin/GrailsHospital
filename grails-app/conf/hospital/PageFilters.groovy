package hospital

class PageFilters {

    def mainNavService

    def filters = {
        all(controller:'*', action:'*') {
            after = { Map model ->
                if (model != null) {
                    model.mainNav = mainNavService.get()
                }
            }
        }
    }
}
