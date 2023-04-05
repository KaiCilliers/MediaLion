import SwiftUI

import SwiftUI

struct ItemRow: View {
    let item: String
    
    var body: some View {
        Text(item)
    }
}

struct ItemRow_Previews: PreviewProvider {
    static var previews: some View {
        ItemRow(item: "single item")
    }
}
